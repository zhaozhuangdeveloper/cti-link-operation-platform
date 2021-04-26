package cn.tinet.operationplatformservice.module.app.deploy.domain.dto;

import lombok.Data;

/**
 * @Time : 2021/4/13 15:39
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : DeploymentYamlReq.java
 * @Software: IntelliJ IDEA
 * @description:
 **/
@Data
public class DeploymentYamlReq {
    private String name;
    private String namespace;
    private String yaml;
}
