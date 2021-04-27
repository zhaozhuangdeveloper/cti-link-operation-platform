package cn.tinet.operationplatformservice.module.app.deploy;

import cn.tinet.operationplatformservice.module.app.deploy.domain.dto.DeployDTO;
import cn.tinet.operationplatformservice.module.app.deploy.domain.vo.DeployListVO;
import cn.tinet.operationplatformservice.module.app.deploy.domain.vo.DeployVo;
import cn.tinet.operationplatformservice.utils.ResultUtil;
import cn.tinet.operationplatformservice.vo.ResponseDTO;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentList;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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


    @GetMapping(value = "/deploy/list")
    public ResponseDTO deployList(@RequestParam(value = "namespaces") List<String> namespaces) {
        logger.debug("invoke deployList begin, params: {}", namespaces);
        List<DeployListVO> deploys = new ArrayList<>();
        namespaces.forEach(namespace -> {
            DeploymentList deploymentList = kubeClient.apps().deployments().inNamespace(namespace).list();
            deploymentList.getItems().forEach(deployment -> {
                deploys.add(new DeployListVO(deployment));
            });
        });
        logger.debug("invoke deployList end, result: {}", deploys);
        return ResultUtil.success(deploys);
    }

    @GetMapping(value = "/deploy/detail")
    public ResponseDTO deployDetail(DeployDTO deployDTO) {
        logger.debug("invoke deployDetail begin, params: {}", deployDTO);
        Deployment deployment = kubeClient.apps()
                .deployments()
                .inNamespace(deployDTO.getNamespace())
                .withName(deployDTO.getName())
                .get();
        DeployVo deployVo = new DeployVo(deployment);
        logger.debug("invoke deployDetail end, result: {}", deployVo);
        return ResultUtil.success(deployVo);
    }

/**
    @PutMapping("/deployment/upgrade")
    public Result upgrade(@RequestBody DeploymentReq deploymentReq){
        logger.info("invoke upgrade begin, params: {}", deploymentReq);
            Map<String , String> params = new HashMap<>();
            deploymentReq.getContainers().forEach(container -> {
                params.put(container.getName(), dockerRepositoryService.image(container.getImageName(), container.getImageTag()));
            });
        kubeClient.apps()
                .deployments()
                .inNamespace(deploymentReq.getNamespace())
                .withName(deploymentReq.getName())
                .rolling()
                .updateImage(params);
        return ResultUtil.success();
    }
*/
    @PutMapping("/deploy/restart")
    public ResponseDTO deployRestart(@RequestBody DeployDTO deployDTO){
        logger.debug("invoke deployRestart begin, params: {}", deployDTO);
        kubeClient.apps()
                .deployments()
                .inNamespace(deployDTO.getNamespace())
                .withName(deployDTO.getName())
                .rolling()
                .restart();
        return ResultUtil.success();
    }
/*
    @PutMapping("/deployment/upgrade/yaml")
    public Result upgradeYaml(@RequestBody DeploymentYamlReq deploymentYamlReq){
        InputStream inputStream = new ByteArrayInputStream(deploymentYamlReq.getYaml().getBytes());
        Deployment deployment = kubeClient.apps()
                .deployments()
                .load(inputStream)
                .get();
        kubeClient.apps()
                .deployments()
                .inNamespace(deploymentYamlReq.getNamespace())
                .withName(deploymentYamlReq.getName())
                .replace(deployment);
        return ResultUtil.success();
    }
*/
}
