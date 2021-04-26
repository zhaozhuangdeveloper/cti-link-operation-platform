package cn.tinet.operationplatformservice.module.app.deploy.domain.dto;

import lombok.Data;

/**
 * @Time : 2021/4/16 16:30
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : TagReq.java
 * @Software: IntelliJ IDEA
 * @description:
 **/
@Data
public class TagReq {
    private String repoNamespaceName;
    private String repoName;
}
