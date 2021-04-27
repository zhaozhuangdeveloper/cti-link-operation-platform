package cn.tinet.operationplatformservice.module.app.pod.domain.vo;

import cn.tinet.operationplatformservice.module.app.container.domain.vo.ContainerVO;
import cn.tinet.operationplatformservice.utils.DateUtil;
import io.fabric8.kubernetes.api.model.ContainerStatus;
import io.fabric8.kubernetes.api.model.Pod;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Time : 2021/4/1 23:48
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : PodListVO.java
 * @Software: IntelliJ IDEA
 **/
@Data
public class PodListVO {
    /** pod id */
    private String id;
    /** 名称 */
    private String name;
    /** 状态 */
    private String status;
    /** 所在主机 */
    private String hostIP;
    /** 实例IP */
    private String podIP;
    /** 创建时间 */
    private Long creationTimestamp;
    /** pod重启次数 pod内全部容器重启次数之和 */
    private Integer restartCount = 0;
    /** pod内正常容器数量 */
    private Integer readyContainers = 0;
    /** pod内容器数量 */
    private Integer totalContainers = 0;
    /** pod内容器 */
    private List<ContainerVO> containers = new ArrayList<>();

    public PodListVO(Pod pod){
        this.id = pod.getMetadata().getUid();
        this.name = pod.getMetadata().getName();
        this.hostIP = pod.getStatus().getHostIP();
        this.podIP = pod.getStatus().getPodIP();
        this.creationTimestamp = DateUtil.getTimestamp(pod.getMetadata().getCreationTimestamp());
        List<ContainerStatus> containerStatuses = pod.getStatus().getContainerStatuses();
        this.totalContainers = containerStatuses.size();
        containerStatuses.forEach(containerStatus -> {
            this.containers.add(new ContainerVO(containerStatus));
            this.restartCount += containerStatus.getRestartCount();
            boolean ready = containerStatus.getReady();
            if (ready) {
                this.readyContainers++;
            }
        });
        this.status = pod.getStatus().getPhase();
    }
}
