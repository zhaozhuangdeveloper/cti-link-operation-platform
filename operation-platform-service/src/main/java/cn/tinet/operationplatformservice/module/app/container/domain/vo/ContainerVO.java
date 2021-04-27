package cn.tinet.operationplatformservice.module.app.container.domain.vo;

import io.fabric8.kubernetes.api.model.ContainerStatus;
import lombok.Data;

/**
 * @Time : 2021/4/4 17:20
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : ContainerVO.java
 * @Software: IntelliJ IDEA
 * @description: 容器列表展示
 **/
@Data
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

    public ContainerVO(ContainerStatus containerStatus){
        this.id = containerStatus.getContainerID();
        this.name = containerStatus.getName();
        this.image = containerStatus.getImage();
        this.restartCount = containerStatus.getRestartCount();
        boolean ready = containerStatus.getReady();
        if (ready){
            this.status = "Running";
        }else{
            this.status = "NotReady";
        }
    }

}
