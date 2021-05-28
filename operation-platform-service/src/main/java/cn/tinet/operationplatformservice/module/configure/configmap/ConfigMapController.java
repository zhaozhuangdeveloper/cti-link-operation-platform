package cn.tinet.operationplatformservice.module.configure.configmap;

import cn.tinet.operationplatformservice.module.configure.configmap.dto.ConfigMapVO;
import cn.tinet.operationplatformservice.utils.ResponseUtil;
import cn.tinet.operationplatformservice.vo.ResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.fabric8.kubernetes.api.model.ConfigMapList;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.internal.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
        List<ConfigMapVO> configMaps = new ArrayList<>();
        ConfigMapList configMapList = kubeClient.configMaps().list();

        configMapList.getItems().forEach(configMap -> {
            ConfigMapVO configMapVO = ConfigMapVO.builder()
                    .id(configMap.getMetadata().getUid())
                    .name(configMap.getMetadata().getName())
                    .namespace(configMap.getMetadata().getNamespace())
                    .build();
            configMap.getMetadata().setManagedFields(null);
            try {
                configMapVO.setYaml(SerializationUtils.dumpWithoutRuntimeStateAsYaml(configMap));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            configMaps.add(configMapVO);
        });
        logger.debug("invoke configMapList end, result: {}", configMaps);
        return ResponseUtil.success(configMaps);
    }

}
