package cn.tinet.operationplatformservice.module.app.deploy.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Time : 2021/4/5 22:19
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : ContainerReq.java
 * @Software: IntelliJ IDEA
 * @description:
 **/
@Data
public class ContainerReq implements Serializable {
    private String name;
    private String imageName;
    private String imageTag;
}
