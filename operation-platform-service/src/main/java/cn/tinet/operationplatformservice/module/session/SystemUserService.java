package cn.tinet.operationplatformservice.module.session;

import cn.tinet.operationplatformservice.module.session.domain.dto.LoginDTO;
import cn.tinet.operationplatformservice.module.session.domain.vo.LoginDetailVO;
import cn.tinet.operationplatformservice.utils.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Time : 2021/3/4 0:43
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : SystemUserService.java
 * @Software: IntelliJ IDEA
 **/
@Service(value = "systemUserService")
public class SystemUserService {

    private Logger logger = LoggerFactory.getLogger(SystemUserService.class);

    @Autowired
    private SystemUserMapper systemUserMapper;


    public LoginDetailVO login(LoginDTO loginDTO) {
        loginDTO.setPassword(MD5Util.getMD5(loginDTO.getPassword()));
        LoginDetailVO loginDetailVO = systemUserMapper.login(loginDTO);
        return loginDetailVO;
    }
}
