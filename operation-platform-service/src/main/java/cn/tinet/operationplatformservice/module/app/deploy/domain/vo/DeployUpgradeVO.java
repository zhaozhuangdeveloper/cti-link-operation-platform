package cn.tinet.operationplatformservice.module.app.deploy.domain.vo;

import cn.tinet.operationplatformservice.module.app.container.domain.vo.ContainerUpgradeVO;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Time : 2021/4/28 15:58
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : DeployUpgradeVO.java
 * @Software: IntelliJ IDEA
 * @description:
 **/
@Data
@Builder
public class DeployUpgradeVO {
    private String name;
    private String namespace;
    private List<ContainerUpgradeVO> containers = new ArrayList<>();
}
