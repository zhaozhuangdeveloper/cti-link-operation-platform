package cn.tinet.operationplatformservice.module.app.container.domain.vo;

import cn.tinet.operationplatformservice.module.app.registry.domain.dto.Tag;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Time : 2021/4/28 15:59
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : ContainerUpgradeVO.java
 * @Software: IntelliJ IDEA
 * @description:
 **/
@Data
@Builder
public class ContainerUpgradeVO {
    private String name;
    private String image;
    private List<Tag> tags;
}
