package cn.tinet.operationplatformservice.module.app.deploy.domain.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.internal.SerializationUtils;
import lombok.Data;

import java.util.Map;

/**
 * @Time : 2021/4/1 14:05
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : DeploymentVo.java
 * @Software: IntelliJ IDEA
 * @description: deployment列表页展示
 **/
@Data
public class DeploymentVo extends DeploymentsVo {

    /** 更新类型 */
    private String strategyType;
    /** 标签 */
    private Map<String, String> labels;
    /** yaml */
    private String yaml;


    public DeploymentVo(Deployment deployment){
       super(deployment);
       this.strategyType = deployment.getSpec().getStrategy().getType();
       this.labels = deployment.getMetadata().getLabels();
       deployment.getMetadata().setManagedFields(null);
       deployment.setStatus(null);
       try {
           this.yaml = SerializationUtils.dumpAsYaml(deployment);
       } catch (JsonProcessingException e) {
           e.printStackTrace();
       }
    }
}
