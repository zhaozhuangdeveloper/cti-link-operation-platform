package cn.tinet.operationplatformservice.module.app.replicaset;

import cn.tinet.operationplatformservice.module.app.deploy.domain.dto.DeployDTO;
import cn.tinet.operationplatformservice.utils.ResponseUtil;
import cn.tinet.operationplatformservice.vo.ResponseDTO;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.ReplicaSetList;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Time : 2021/5/26 15:38
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : ReplicasetsController.java
 * @Software: IntelliJ IDEA
 * @description:
 **/
@RestController
@RequestMapping("/app")
public class ReplicaSetController {

    private static final Logger logger = LoggerFactory.getLogger(ReplicaSetController.class);

    @Autowired
    KubernetesClient kubeClient;

    @GetMapping("/replicaset/list")
    public ResponseDTO replicaSetList(DeployDTO deployDTO){
        logger.debug("invoke replicaSetList begin, params: {}", deployDTO);
        Deployment deployment = kubeClient.apps()
                .deployments()
                .inNamespace(deployDTO.getNamespace())
                .withName(deployDTO.getName())
                .get();
        ReplicaSetList replicaSetList = kubeClient.apps()
                .replicaSets()
                .inNamespace(deployDTO.getNamespace())
                .withLabelSelector(deployment.getSpec().getSelector())
                .list();
        logger.info("invoke replicaSetList end, result: {}", replicaSetList);
        return ResponseUtil.success(replicaSetList);
    }

}
