package cn.tinet.operationplatformservice.module.app.registry.domain.dto;

import lombok.Data;

/**
 * @Time : 2021/4/28 23:01
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : TagDTO.java
 * @Software: IntelliJ IDEA
 * @description:
 **/
@Data
public class TagDTO {
    /** 仓库名称 */
    private String name;
    /** 仓库版本 */
    private String[] tags;
}
