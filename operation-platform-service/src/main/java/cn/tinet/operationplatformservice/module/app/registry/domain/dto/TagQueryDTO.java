package cn.tinet.operationplatformservice.module.app.registry.domain.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @Time : 2021/4/16 16:30
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : TagQueryDTO.java
 * @Software: IntelliJ IDEA
 * @description:
 **/
@Data
@Builder
public class TagQueryDTO {
    private String namespace;
    private String name;
}
