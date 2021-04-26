package cn.tinet.operationplatformservice.module.app.deploy;

import cn.tinet.operationplatformservice.utils.ResultUtil;
import cn.tinet.operationplatformservice.vo.ResponseDTO;
import cn.tinet.operationplatformservice.module.app.deploy.domain.dto.PodReq;
import cn.tinet.operationplatformservice.module.app.deploy.domain.dto.PodVo;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Time : 2021/3/2 23:58
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : PodController.java
 * @Software: IntelliJ IDEA
 **/
@RestController
@RequestMapping(value = "/app")
public class PodController {

    private static final Logger logger = LoggerFactory.getLogger(PodController.class);

    @Autowired
    KubernetesClient kubeClient = null;

    @GetMapping(value = "/pods")
    public ResponseDTO pods(PodReq podReq) {
        logger.info("invoke pods begin, params: {}", podReq);
        Deployment deployment = kubeClient.apps()
                .deployments()
                .inNamespace(podReq.getNamespaceName())
                .withName(podReq.getDeploymentName())
                .get();
        PodList podList = kubeClient.pods()
                .inNamespace(podReq.getNamespaceName())
                .withLabelSelector(deployment.getSpec().getSelector())
                .list();
        List<PodVo> podVoList = new ArrayList<>();
        podList.getItems().forEach(pod -> {
            podVoList.add(new PodVo(pod));
        });
        //logger.info("invoke pods end, result: {}", podVoList);
        return ResultUtil.success(podVoList);
    }

}
