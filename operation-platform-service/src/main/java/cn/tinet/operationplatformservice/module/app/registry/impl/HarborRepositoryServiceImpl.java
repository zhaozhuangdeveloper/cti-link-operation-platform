package cn.tinet.operationplatformservice.module.app.registry.impl;

import cn.tinet.operationplatformservice.module.app.registry.DockerRepositoryService;
import cn.tinet.operationplatformservice.module.app.registry.domain.dto.TagQueryDTO;
import cn.tinet.operationplatformservice.module.app.registry.domain.vo.TagListVO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @Time : 2021/5/11 9:57
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : HarborRepositoryServiceImpl.java
 * @Software: IntelliJ IDEA
 * @description:
 **/
@Service("harborReposioryService")
@ConditionalOnProperty(value = "docker.repository.type", havingValue = "harbor")
public class HarborRepositoryServiceImpl implements DockerRepositoryService {
    @Override
    public TagListVO tagList(TagQueryDTO tagQueryDTO) {
        return null;
    }
}
