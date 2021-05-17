package cn.tinet.operationplatformservice.module.app.namespace;

import cn.tinet.operationplatformservice.module.app.namespace.domain.vo.NamespaceVO;
import cn.tinet.operationplatformservice.utils.ResponseUtil;
import cn.tinet.operationplatformservice.vo.ResponseDTO;
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
 * @Time : 2021/5/11 14:58
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : NamespaceController.java
 * @Software: IntelliJ IDEA
 * @description:
 **/
@RestController
@RequestMapping("/app")
public class NamespaceController {

    private static final Logger logger = LoggerFactory.getLogger(NamespaceController.class);

    @Autowired
    KubernetesClient kubeClient = null;

    @GetMapping(value = "/namespace/list")
    public ResponseDTO namespaceList() {
        List<NamespaceVO> namespaces = new ArrayList<>();
        kubeClient.namespaces().list().getItems().forEach(namespace -> {
            NamespaceVO namespaceVO = NamespaceVO.builder()
                    .label(namespace.getMetadata().getName())
                    .value(namespace.getMetadata().getName())
                    .build();
            namespaces.add(namespaceVO);
        });
        logger.debug("invoke namespaceList end, result: {}", namespaces);
        return ResponseUtil.success(namespaces);
    }
}
