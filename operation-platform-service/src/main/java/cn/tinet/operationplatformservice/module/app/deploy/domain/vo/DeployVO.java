package cn.tinet.operationplatformservice.module.app.deploy.domain.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * @Time : 2021/4/1 14:05
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : DeployVO.java
 * @Software: IntelliJ IDEA
 * @description: deploy详情展示
 **/
@Data
@Builder
public class DeployVO {
    /** deployment id */
    private String id;
    /** 名称 */
    private String name;
    /** 状态 */
    private String status;
    /** 正常的副本数 */
    private Integer readyReplicas;
    /** 正在更新的副本数 */
    private Integer updateReplicas;
    /** 全部副本数 */
    private Integer replicas;
    /** 命名空间 */
    private String namespace;
    /** 创建时间 */
    private Long creationTimestamp;
    /** 更新类型 */
    private String strategyType;
    /** 标签 */
    private Map<String, String> labels;
    /** yaml */
    private String yaml;
}
