package cn.tinet.operationplatformservice.module.app.registry.impl;

import cn.tinet.operationplatformservice.module.app.registry.DockerRepositoryService;
import cn.tinet.operationplatformservice.module.app.registry.domain.dto.Tag;
import cn.tinet.operationplatformservice.module.app.registry.domain.dto.TagDTO;
import cn.tinet.operationplatformservice.module.app.registry.domain.dto.TagListDTO;
import cn.tinet.operationplatformservice.module.app.registry.domain.vo.TagListVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

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
    public TagListVO tagList(TagListDTO tagListDTO) {
        RestTemplate restTemplate = new RestTemplate();
        TagDTO tagDTO = restTemplate.getForObject(registryEndpoint + "/v2/"
                + tagListDTO.getRepoNamespace() + "/" + tagListDTO.getRepoName() + "/tags/list", TagDTO.class);
        TagListVO tagListVO = TagListVO.builder()
                .name(tagDTO.getName())
                .tags(new ArrayList<>())
                .build();

        for (String label : tagDTO.getTags()) {
            Tag tag = new Tag();
            tag.setLabel(label);
            tag.setValue(registryEndpoint.substring(7) +"/" + tagListDTO.getRepoNamespace()
                    + "/" + tagListDTO.getRepoName() + ":" + label);
            tagListVO.getTags().add(tag);
        }
        return tagListVO;
    }
}
