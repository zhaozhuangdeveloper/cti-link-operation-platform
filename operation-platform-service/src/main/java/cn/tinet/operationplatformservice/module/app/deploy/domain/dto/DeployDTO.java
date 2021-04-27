package cn.tinet.operationplatformservice.module.app.deploy.domain.dto;

import lombok.Data;

/**
 * @Time : 2021/4/16 21:56
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : DeployRestartDTO.java
 * @Software: IntelliJ IDEA
 * @description:
 **/
@Data
public class DeployDTO {
    private String name;
    private String namespace;
}
