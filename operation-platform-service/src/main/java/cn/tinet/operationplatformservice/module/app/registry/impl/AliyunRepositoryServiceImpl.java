package cn.tinet.operationplatformservice.module.app.registry.impl;

import cn.tinet.operationplatformservice.module.app.registry.DockerRepositoryService;
import cn.tinet.operationplatformservice.module.app.registry.domain.dto.TagListDTO;
import cn.tinet.operationplatformservice.module.app.registry.domain.vo.TagListVO;
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
    public TagListVO tagList(TagListDTO tagListDTO) {
        return null;
    }
}
