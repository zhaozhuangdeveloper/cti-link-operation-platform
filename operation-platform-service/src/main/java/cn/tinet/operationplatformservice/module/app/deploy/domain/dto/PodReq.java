package cn.tinet.operationplatformservice.module.app.deploy.domain.dto;

import lombok.Data;

/**
 * @Time : 2021/4/1 23:53
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : PodReq.java
 * @Software: IntelliJ IDEA
 **/
@Data
public class PodReq {
    private String namespaceName;
    private String deploymentName;
}
