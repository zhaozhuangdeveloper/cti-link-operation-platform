package cn.tinet.operationplatformservice.module.app.registry;

import cn.tinet.operationplatformservice.utils.ResultUtil;
import cn.tinet.operationplatformservice.vo.ResponseDTO;
import cn.tinet.operationplatformservice.module.app.registry.domain.dto.TagReq;
import cn.tinet.operationplatformservice.module.app.registry.domain.vo.TagsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Time : 2021/4/5 11:53
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : RegistryController.java
 * @Software: IntelliJ IDEA
 * @description:
 **/
@RestController
@RequestMapping("/app")
public class RegistryController {

    @Autowired
    private DockerRepositoryService dockerRepositoryService = null;

    private static final Logger logger = LoggerFactory.getLogger(RegistryController.class);

    @GetMapping("/registry/tags")
    public ResponseDTO tags(@RequestBody TagReq tagReq){
        TagsVo tagsVo = dockerRepositoryService.tags(tagReq.getRepoNamespaceName(), tagReq.getRepoName());
        return ResultUtil.success(tagsVo);
    }
}
