package cn.tinet.operationplatformservice.module.session;

import cn.tinet.operationplatformservice.module.session.domain.dto.LoginDTO;
import cn.tinet.operationplatformservice.module.session.domain.vo.LoginDetailVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Time : 2021/3/4 0:37
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : SystemUserMapper.java
 * @Software: IntelliJ IDEA
 **/
@Mapper
public interface SystemUserMapper {
    public LoginDetailVO login(LoginDTO loginDTO);
}
