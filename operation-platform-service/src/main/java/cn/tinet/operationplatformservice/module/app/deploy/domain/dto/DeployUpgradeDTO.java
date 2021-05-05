package cn.tinet.operationplatformservice.module.app.deploy.domain.dto;

import cn.tinet.operationplatformservice.module.app.container.domain.dto.ContainerDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Time : 2021/5/5 19:40
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : DeployUpgradeDTO.java
 * @Software: IntelliJ IDEA
 * @description:
 **/
@Data
public class DeployUpgradeDTO {
    private String name;
    private String namespace;
    private List<ContainerDTO> containers = new ArrayList<>();
}
