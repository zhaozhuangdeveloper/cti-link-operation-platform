package cn.tinet.operationplatformservice.module.app.pod;

import cn.tinet.operationplatformservice.module.app.deploy.domain.dto.DeployDTO;
import cn.tinet.operationplatformservice.utils.ResultUtil;
import cn.tinet.operationplatformservice.vo.ResponseDTO;
import cn.tinet.operationplatformservice.module.app.pod.domain.vo.PodListVO;
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

    @GetMapping(value = "/pod/list")
    public ResponseDTO podList(DeployDTO deployDTO) {
        logger.debug("invoke podList begin, params: {}", deployDTO);
        Deployment deployment = kubeClient.apps()
                .deployments()
                .inNamespace(deployDTO.getNamespace())
                .withName(deployDTO.getName())
                .get();
        PodList podList = kubeClient.pods()
                .inNamespace(deployDTO.getNamespace())
                .withLabelSelector(deployment.getSpec().getSelector())
                .list();
        List<PodListVO> pods = new ArrayList<>();
        podList.getItems().forEach(pod -> {
            pods.add(new PodListVO(pod));
        });
        logger.debug("invoke podList end, result: {}", pods);
        return ResultUtil.success(pods);
    }

}
