package cn.tinet.operationplatformservice.module.app.deploy.domain.dto;

import lombok.Data;

/**
 * @Time : 2021/4/5 12:49
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : TagsVo.java
 * @Software: IntelliJ IDEA
 * @description:
 **/
@Data
public class TagsVo {
    /** 仓库名称 */
    private String repoName;
    /** 仓库版本 */
    private String[] repoTags;
}
