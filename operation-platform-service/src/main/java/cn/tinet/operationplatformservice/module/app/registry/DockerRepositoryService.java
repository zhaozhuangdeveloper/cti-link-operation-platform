package cn.tinet.operationplatformservice.module.app.registry;


import cn.tinet.operationplatformservice.module.app.registry.domain.dto.TagQueryDTO;
import cn.tinet.operationplatformservice.module.app.registry.domain.vo.TagListVO;

/**
 * @Time : 2021/4/16 15:37
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : DockerRepositoryService.java
 * @Software: IntelliJ IDEA
 * @description:
 **/
public interface DockerRepositoryService {
    public TagListVO tagList(TagQueryDTO tagQueryDTO);
}
