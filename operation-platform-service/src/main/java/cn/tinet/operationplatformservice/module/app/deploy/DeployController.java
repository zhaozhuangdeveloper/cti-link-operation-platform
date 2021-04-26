package cn.tinet.operationplatformservice.module.app.deploy;

import cn.tinet.operationplatformservice.module.app.DockerRepositoryService;
import cn.tinet.operationplatformservice.utils.ResultUtil;
import cn.tinet.operationplatformservice.vo.ResponseDTO;
import cn.tinet.operationplatformservice.module.app.deploy.domain.dto.DeploymentVo;
import cn.tinet.operationplatformservice.module.app.deploy.domain.dto.DeploymentsVo;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentList;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    DockerRepositoryService dockerRepositoryService = null;

    @GetMapping(value = "/deploy/list")
    public ResponseDTO deployments(@RequestParam(value = "namespaceNames") List<String> namespaceNames) {
        logger.debug("invoke deployments begin, params: {}", namespaceNames);
        List<DeploymentsVo> deploymentsVoList = new ArrayList<>();
        namespaceNames.forEach(namespaceName -> {
            DeploymentList deploymentList = kubeClient.apps().deployments().inNamespace(namespaceName).list();
            deploymentList.getItems().forEach(deployment -> {
                deploymentsVoList.add(new DeploymentsVo(deployment));
            });
        });
        logger.debug("invoke deployments end, result: {}", deploymentsVoList);
        return ResultUtil.success(deploymentsVoList);
    }

    @GetMapping(value = "/deployment")
    public ResponseDTO deployment(String namespaceName, String deploymentName) {
        logger.debug("invoke deployments begin, params: {}", deploymentName);
        Deployment deployment = kubeClient.apps()
                .deployments()
                .inNamespace(namespaceName)
                .withName(deploymentName)
                .get();
        DeploymentVo deploymentVo = new DeploymentVo(deployment);
        logger.debug("invoke deployment end, result: {}", deploymentVo);
        return ResultUtil.success(deploymentVo);
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

    @PutMapping("/deployment/restart")
    public Result restart(@RequestBody DeploymentReq deploymentReq){
        logger.info("invoke restart begin, params: {}", deploymentReq);
        kubeClient.apps()
                .deployments()
                .inNamespace(deploymentReq.getNamespace())
                .withName(deploymentReq.getName())
                .rolling()
                .restart();
        return ResultUtil.success();
    }

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
