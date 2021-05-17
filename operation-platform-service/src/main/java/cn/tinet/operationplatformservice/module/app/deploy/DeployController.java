package cn.tinet.operationplatformservice.module.app.deploy;

import cn.tinet.operationplatformservice.module.app.container.domain.vo.ContainerUpgradeVO;
import cn.tinet.operationplatformservice.module.app.deploy.domain.dto.DeployDTO;
import cn.tinet.operationplatformservice.module.app.deploy.domain.dto.DeployUpgradeDTO;
import cn.tinet.operationplatformservice.module.app.deploy.domain.dto.DeployYamlDTO;
import cn.tinet.operationplatformservice.module.app.deploy.domain.vo.DeployListVO;
import cn.tinet.operationplatformservice.module.app.deploy.domain.vo.DeployUpgradeVO;
import cn.tinet.operationplatformservice.module.app.deploy.domain.vo.DeployVO;
import cn.tinet.operationplatformservice.module.app.registry.DockerRepositoryService;
import cn.tinet.operationplatformservice.module.app.registry.domain.dto.TagQueryDTO;
import cn.tinet.operationplatformservice.utils.DateUtil;
import cn.tinet.operationplatformservice.utils.ResponseUtil;
import cn.tinet.operationplatformservice.vo.ResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentList;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.internal.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Time : 2021/4/1 10:17
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : DeploymentController.java
 * @Software: IntelliJ IDEA
 **/
@RestController
@RequestMapping("/app")
public class DeployController {
    private static final Logger logger = LoggerFactory.getLogger(DeployController.class);

    @Autowired
    KubernetesClient kubeClient = null;

    @Autowired
    private DockerRepositoryService dockerRepositoryService = null;


    @GetMapping(value = "/deploy/list")
    public ResponseDTO deployList() {
        logger.debug("invoke deployList begin, params: {}");
        List<DeployListVO> deploys = new ArrayList<>();
        DeploymentList deploymentList = kubeClient.apps().deployments().list();
        deploymentList.getItems().forEach(deployment -> {
            DeployListVO deployListVO = DeployListVO.builder()
                    .id(deployment.getMetadata().getUid())
                    .name(deployment.getMetadata().getName())
                    .readyReplicas(deployment.getStatus().getReadyReplicas())
                    .updateReplicas(deployment.getStatus().getUpdatedReplicas())
                    .replicas(deployment.getStatus().getReplicas())
                    .namespace(deployment.getMetadata().getNamespace())
                    .creationTimestamp(DateUtil.getTimestamp(deployment.getMetadata().getCreationTimestamp()))
                    .build();
            if (deployListVO.getReplicas() == null){
                deployListVO.setReplicas(0);
            }
            if (deployListVO.getReadyReplicas() == null){
                deployListVO.setReadyReplicas(0);
            }
            if (deployListVO.getUpdateReplicas() == null){
                deployListVO.setUpdateReplicas(0);
            }
            deployListVO.setStatus(getStatus(deployListVO.getReadyReplicas(), deployListVO.getUpdateReplicas(),
                    deployListVO.getReplicas()));
            List<String> images = new ArrayList<>();
            deployment.getSpec().getTemplate().getSpec().getContainers().forEach(container -> {
                images.add(container.getImage());
            });
            deployListVO.setImages(images);
            deploys.add(deployListVO);
        });
        logger.debug("invoke deployList end, result: {}", deploys);
        return ResponseUtil.success(deploys);
    }



    @GetMapping(value = "/deploy/detail")
    public ResponseDTO deployDetail(DeployDTO deployDTO) {
        logger.debug("invoke deployDetail begin, params: {}", deployDTO);
        Deployment deployment = kubeClient.apps()
                .deployments()
                .inNamespace(deployDTO.getNamespace())
                .withName(deployDTO.getName())
                .get();
        DeployVO deployVo = DeployVO.builder()
                .id(deployment.getMetadata().getUid())
                .name(deployment.getMetadata().getName())
                .readyReplicas(deployment.getStatus().getReadyReplicas())
                .updateReplicas(deployment.getStatus().getUpdatedReplicas())
                .replicas(deployment.getStatus().getReplicas())
                .namespace(deployment.getMetadata().getNamespace())
                .creationTimestamp(DateUtil.getTimestamp(deployment.getMetadata().getCreationTimestamp()))
                .strategyType(deployment.getSpec().getStrategy().getType())
                .labels(deployment.getSpec().getTemplate().getMetadata().getLabels())
                .build();
        deployVo.setStatus(getStatus(deployVo.getReadyReplicas(), deployVo.getUpdateReplicas(),
                deployVo.getReplicas()));
        deployment.getMetadata().setManagedFields(null);
        deployment.setStatus(null);
        try {
            deployVo.setYaml(SerializationUtils.dumpWithoutRuntimeStateAsYaml(deployment));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        logger.debug("invoke deployDetail end, result: {}", deployVo);
        return ResponseUtil.success(deployVo);
    }

    @GetMapping(value = "/deploy/upgrade")
    public ResponseDTO getDeployUpgrade(DeployDTO deployDTO) {
        logger.debug("invoke getDeployUpgrade begin, params: {}", deployDTO);
        Deployment deployment = kubeClient.apps()
                .deployments()
                .inNamespace(deployDTO.getNamespace())
                .withName(deployDTO.getName())
                .get();
        DeployUpgradeVO deployUpgradeVO = DeployUpgradeVO.builder()
                .name(deployment.getMetadata().getName())
                .namespace(deployment.getMetadata().getNamespace())
                .containers(new ArrayList<>())
                .build();
        deployment.getSpec().getTemplate().getSpec().getContainers().forEach(container -> {
            ContainerUpgradeVO containerUpgradeVO = ContainerUpgradeVO.builder()
                    .name(container.getName())
                    .image(container.getImage())
                    .build();
            TagQueryDTO tagQueryDTO  = TagQueryDTO.builder()
                    .namespace(container.getImage().split("/")[1])
                    .name(container.getImage().split("/")[2].split(":")[0])
                    .build();
            containerUpgradeVO.setTags(dockerRepositoryService.tagList(tagQueryDTO).getTags());
            deployUpgradeVO.getContainers().add(containerUpgradeVO);
        });
        logger.debug("invoke getDeployUpgrade end, result: {}", deployUpgradeVO);
        return ResponseUtil.success(deployUpgradeVO);
    }


    @PutMapping("/deploy/upgrade")
    public ResponseDTO deployUpgrade(@RequestBody DeployUpgradeDTO deployUpgradeDTO){
        logger.debug("invoke deployUpgrade begin, params: {}", deployUpgradeDTO);
            Map<String , String> params = new HashMap<>();
            deployUpgradeDTO.getContainers().forEach(container -> {
                params.put(container.getName(), container.getImage());
            });
        kubeClient.apps()
                .deployments()
                .inNamespace(deployUpgradeDTO.getNamespace())
                .withName(deployUpgradeDTO.getName())
                .rolling()
                .updateImage(params);
        return ResponseUtil.success();
    }

    @PutMapping("/deploy/restart")
    public ResponseDTO deployRestart(@RequestBody DeployDTO deployDTO){
        logger.debug("invoke deployRestart begin, params: {}", deployDTO);
        kubeClient.apps()
                .deployments()
                .inNamespace(deployDTO.getNamespace())
                .withName(deployDTO.getName())
                .rolling()
                .restart();
        return ResponseUtil.success();
    }

    @PutMapping("/deploy/upgrade/yaml")
    public ResponseDTO deployUpgradeYaml(@RequestBody DeployYamlDTO deployYamlDTO){
        InputStream inputStream = new ByteArrayInputStream(deployYamlDTO.getYaml().getBytes());
        kubeClient.load(inputStream).createOrReplace();
        return ResponseUtil.success();
    }

    public String getStatus(int readyReplicas, int updateReplicas, int replicas){
        String status = "";
        if(updateReplicas != replicas){
            status = "升级/回滚中";
        }else if(readyReplicas == replicas){
            status = "运行中";
        }else if(readyReplicas == 0){
            status = "未就绪";
        }else if (readyReplicas > 0){
            status = "可用";
        }
        return status;
    }
}
