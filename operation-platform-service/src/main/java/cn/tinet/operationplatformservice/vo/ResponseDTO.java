package cn.tinet.operationplatformservice.vo;

import lombok.Data;

/**
 * @Time : 2021/3/3 22:17
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : ResponseDTO.java
 * @Software: IntelliJ IDEA
 **/
@Data
public class ResponseDTO<T> {
    private String code;
    private String description;
    private T data;
}
