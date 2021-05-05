package cn.tinet.operationplatformservice.module.app.container.domain.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @Time : 2021/4/4 17:20
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : ContainerVO.java
 * @Software: IntelliJ IDEA
 * @description: 容器状态展示
 **/
@Data
@Builder
public class ContainerVO {

    /** 容器ID */
    private String id;
    /** 容器名称 */
    private String name;
    /** 容器状态 */
    private String status;
    /** 镜像 */
    private String image;
    /** 容器重启次数 */
    private Integer restartCount;

}
