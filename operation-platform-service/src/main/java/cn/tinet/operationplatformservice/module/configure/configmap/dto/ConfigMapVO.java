package cn.tinet.operationplatformservice.module.configure.configmap.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @Time : 2021/5/17 17:15
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : ConfigMapVO.java
 * @Software: IntelliJ IDEA
 * @description:
 **/
@Data
@Builder
public class ConfigMapVO {
    private String id;
    /** 名称 */
    private String name;
    /** 命名空间 */
    private String namespace;
    /** 创建时间 */
    private Long creationTimestamp;
    /** yaml */
    private String yaml;
}
