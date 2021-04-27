package cn.tinet.operationplatformservice.module.app.registry;


import cn.tinet.operationplatformservice.module.app.registry.domain.vo.TagsVo;

/**
 * @Time : 2021/4/16 15:37
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : DockerRepositoryService.java
 * @Software: IntelliJ IDEA
 * @description:
 **/
public interface DockerRepositoryService {
    public TagsVo tags(String repoNamespaceName, String repoName);

    public String image(String repoNamespaceName, String repoName, String tag);
}
