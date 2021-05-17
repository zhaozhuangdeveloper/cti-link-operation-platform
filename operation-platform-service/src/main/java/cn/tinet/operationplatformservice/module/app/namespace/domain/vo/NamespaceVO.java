package cn.tinet.operationplatformservice.module.app.namespace.domain.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @Time : 2021/5/11 15:29
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : NamespaceVO.java
 * @Software: IntelliJ IDEA
 * @description:
 **/
@Data
@Builder
public class NamespaceVO {
    private String label;
    private String value;
}
