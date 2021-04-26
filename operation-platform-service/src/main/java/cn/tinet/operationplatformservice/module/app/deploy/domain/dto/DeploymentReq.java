package cn.tinet.operationplatformservice.module.app.deploy.domain.dto;

import lombok.Data;

/**
 * @Time : 2021/4/16 21:56
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : DeploymentReq.java
 * @Software: IntelliJ IDEA
 * @description:
 **/
@Data
public class DeploymentReq {
    private String namespaceName;
    private String deploymentName;
}
