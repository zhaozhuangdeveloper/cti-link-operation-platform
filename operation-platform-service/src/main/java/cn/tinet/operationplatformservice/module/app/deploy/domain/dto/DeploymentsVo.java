package cn.tinet.operationplatformservice.module.app.deploy.domain.dto;

import io.fabric8.kubernetes.api.model.apps.Deployment;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Time : 2021/4/16 16:51
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : DeploymentsVo.java
 * @Software: IntelliJ IDEA
 * @description:
 **/
@Data
@NoArgsConstructor
public class DeploymentsVo{
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
    private String namespaceName;
    /** 创建时间 */
    private String creationTimestamp;

    public DeploymentsVo(Deployment deployment){
        this.id = deployment.getMetadata().getUid();
        this.name = deployment.getMetadata().getName();
        this.readyReplicas = deployment.getStatus().getReadyReplicas();
        this.updateReplicas = deployment.getStatus().getUpdatedReplicas();
        this.replicas = deployment.getStatus().getReplicas();
        this.namespaceName = deployment.getMetadata().getNamespace();
        this.creationTimestamp = deployment.getMetadata().getCreationTimestamp();
        if (this.readyReplicas == null){
            this.readyReplicas = 0;
        }
        if (this.updateReplicas == null) {
            this.updateReplicas = 0;
        }
        if(this.updateReplicas != this.replicas){
            this.status = "升级/回滚中";
        }else if(this.readyReplicas == this.replicas){
            this.status = "运行中";
        }else if(this.readyReplicas == 0){
            this.status = "未就绪";
        }else if (this.readyReplicas > 0){
            this.status = "可用";
        }
    }
}
