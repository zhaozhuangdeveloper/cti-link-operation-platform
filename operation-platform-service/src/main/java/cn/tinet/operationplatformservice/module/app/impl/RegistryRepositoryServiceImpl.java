package cn.tinet.operationplatformservice.module.app.impl;

import cn.tinet.operationplatformservice.module.app.DockerRepositoryService;
import cn.tinet.operationplatformservice.module.app.deploy.domain.dto.TagsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Time : 2021/4/16 15:39
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : RegistryRepositoryServiceImpl.java
 * @Software: IntelliJ IDEA
 * @description:
 **/
@Service("registryRepositoryService")
@ConditionalOnProperty(value = "docker.repository.type", havingValue = "registry")
public class RegistryRepositoryServiceImpl implements DockerRepositoryService {

    private static final Logger logger = LoggerFactory.getLogger(RegistryRepositoryServiceImpl.class);

    @Value("${registry.endpoint}")
    public String registryEndpoint = null;

    @Override
    public TagsVo tags(String repoNamespaceName, String repoName) {
        RestTemplate restTemplate = new RestTemplate();
        TagsVo tagsVo = restTemplate.getForObject(registryEndpoint + "/v2/"
                + repoNamespaceName + "/" + repoName + "/tags/list", TagsVo.class);
        return tagsVo;
    }

    @Override
    public String image(String repoNamespaceName, String repoName, String tag) {
        return registryEndpoint + "/" + repoNamespaceName + "/" + repoName + ":" + tag;
    }
}
