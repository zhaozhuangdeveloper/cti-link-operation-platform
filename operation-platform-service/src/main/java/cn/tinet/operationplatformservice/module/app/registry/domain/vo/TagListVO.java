package cn.tinet.operationplatformservice.module.app.registry.domain.vo;

import cn.tinet.operationplatformservice.module.app.registry.domain.dto.Tag;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Time : 2021/4/5 12:49
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : TagListVO.java
 * @Software: IntelliJ IDEA
 * @description:
 **/
@Data
@Builder
public class TagListVO {
    /** 仓库名称 */
    private String name;
    /** 仓库版本 */
    private List<Tag> tags;
}
