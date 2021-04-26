package cn.tinet.operationplatformservice.module.app.impl;

import cn.tinet.operationplatformservice.module.app.DockerRepositoryService;
import cn.tinet.operationplatformservice.module.app.deploy.domain.dto.TagsVo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @Time : 2021/4/16 16:19
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : AliyunRepositoryServiceImpl.java
 * @Software: IntelliJ IDEA
 * @description:
 **/
@Service("aliyunRepositoryService")
@ConditionalOnProperty(value = "docker.repository.type", havingValue = "aliyun")
public class AliyunRepositoryServiceImpl implements DockerRepositoryService {
    @Override
    public TagsVo tags(String repoNamespaceName, String repoName) {
        return null;
    }

    @Override
    public String image(String repoNamespaceName, String repoName, String tag) {
        return null;
    }
}
