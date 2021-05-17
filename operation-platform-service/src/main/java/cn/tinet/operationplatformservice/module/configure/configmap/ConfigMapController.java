package cn.tinet.operationplatformservice.module.configure.configmap;

import cn.tinet.operationplatformservice.utils.ResponseUtil;
import cn.tinet.operationplatformservice.vo.ResponseDTO;
import io.fabric8.kubernetes.api.model.ConfigMapList;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Time : 2021/5/17 14:42
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : ConfigMapController.java
 * @Software: IntelliJ IDEA
 * @description:
 **/
@RestController
@RequestMapping("/configure")
public class ConfigMapController {

    private Logger logger = LoggerFactory.getLogger(ConfigMapController.class);

    @Autowired
    KubernetesClient kubeClient = null;

    @GetMapping("/configMap/list")
    public ResponseDTO configMapList(){
        ConfigMapList configMapList = kubeClient.configMaps().list();
        logger.info("invoke configMapList end, result: {}", configMapList);
        return ResponseUtil.success();
    }

}
