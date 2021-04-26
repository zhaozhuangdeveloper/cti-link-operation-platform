package cn.tinet.operationplatformservice.module.session.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Time : 2021/4/27 0:27
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : LoginDetailVO.java
 * @Software: IntelliJ IDEA
 * @description: 用户信息
 **/
@Data
public class LoginDetailVO {
    private Integer id;
    private String userName;
    private String realName;
    private String description;
    private String email;
    private String mobile;
    private Date createTime;
}
