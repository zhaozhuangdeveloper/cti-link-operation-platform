package cn.tinet.operationplatformservice.module.app.pod;

import cn.tinet.operationplatformservice.module.app.container.domain.vo.ContainerVO;
import cn.tinet.operationplatformservice.module.app.deploy.domain.dto.DeployDTO;
import cn.tinet.operationplatformservice.module.app.pod.domain.vo.PodListVO;
import cn.tinet.operationplatformservice.utils.DateUtil;
import cn.tinet.operationplatformservice.utils.ResultUtil;
import cn.tinet.operationplatformservice.vo.ResponseDTO;
import io.fabric8.kubernetes.api.model.ContainerStatus;
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
import java.util.concurrent.atomic.AtomicInteger;

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
            PodListVO podListVO = PodListVO.builder()
                    .id(pod.getMetadata().getUid())
                    .name(pod.getMetadata().getName())
                    .hostIP(pod.getStatus().getHostIP())
                    .podIP(pod.getStatus().getPodIP())
                    .creationTimestamp(DateUtil.getTimestamp(pod.getMetadata().getCreationTimestamp()))
                    .status(pod.getStatus().getPhase())
                    .build();
            AtomicInteger restartCount = new AtomicInteger();
            AtomicInteger readyContainers = new AtomicInteger();
            List<ContainerStatus> containerStatuses = pod.getStatus().getContainerStatuses();
            podListVO.setTotalContainers(containerStatuses.size());
            List<ContainerVO> containers = new ArrayList<>();
            containerStatuses.forEach(containerStatus -> {
                ContainerVO containerVO = ContainerVO.builder()
                        .id(containerStatus.getContainerID())
                        .name(containerStatus.getName())
                        .image(containerStatus.getImage())
                        .restartCount(containerStatus.getRestartCount())
                        .build();
                boolean ready = containerStatus.getReady();
                restartCount.getAndAdd(containerStatus.getRestartCount());
                if (ready){
                    containerVO.setStatus("Running");
                    readyContainers.getAndAdd(1);
                    }else{
                    containerVO.setStatus("NotReady");
                }
                containers.add(containerVO);
            });
            podListVO.setRestartCount(restartCount.get());
            podListVO.setReadyContainers(readyContainers.get());
            podListVO.setContainers(containers);
            pods.add(podListVO);
        });

        logger.debug("invoke podList end, result: {}", pods);
        return ResultUtil.success(pods);
    }

}
