package cn.tinet.operationplatformservice.module.session.domain.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Time : 2021/3/4 0:24
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : SystemUser.java
 * @Software: IntelliJ IDEA
 * @description: 用户信息
 **/
@Data
public class SystemUser {
    private Integer id;
    private String userName;
    private String realName;
    private String password;
    private String description;
    private String email;
    private String mobile;
    private Date createTime;
}
