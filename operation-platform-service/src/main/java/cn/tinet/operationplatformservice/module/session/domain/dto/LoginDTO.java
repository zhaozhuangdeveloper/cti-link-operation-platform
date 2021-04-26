package cn.tinet.operationplatformservice.module.session.domain.dto;

import lombok.Data;

/**
 * @Time : 2021/4/27 0:08
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : LoginDTO.java
 * @Software: IntelliJ IDEA
 * @description:
 **/
@Data
public class LoginDTO {
    /* 登录用户名 */
    private String userName;
    /* 登录密码 */
    private String password;
}
