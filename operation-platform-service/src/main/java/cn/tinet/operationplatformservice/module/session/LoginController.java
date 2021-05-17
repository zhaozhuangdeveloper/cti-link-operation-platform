package cn.tinet.operationplatformservice.module.session;

import cn.tinet.operationplatformservice.module.session.domain.dto.LoginDTO;
import cn.tinet.operationplatformservice.module.session.domain.vo.LoginDetailVO;
import cn.tinet.operationplatformservice.utils.RedisUtil;
import cn.tinet.operationplatformservice.utils.ResponseUtil;
import cn.tinet.operationplatformservice.vo.ResponseDTO;
import cn.tinet.operationplatformservice.vo.ResponseEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Time : 2021/3/3 0:33
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : LoginController.java
 * @Software: IntelliJ IDEA
 **/
@RestController
@RequestMapping("/session")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Value("${login.expire}")
    private Long loginExpire = null;

    @Autowired
    private SystemUserService userService = null;

    @Autowired
    private RedisUtil<String> redisStrUtil = null;

    @Autowired
    private RedisUtil<Integer> redisIntUtil = null;

    @PostMapping("/login")
    public ResponseDTO login(@RequestBody LoginDTO loginDTO){
        logger.debug("invote login begin, params: {}", loginDTO);
        LoginDetailVO loginDetailVO = userService.login(loginDTO);
        ResponseDTO responseDTO = null;
        if (null != loginDetailVO){
            //将token写入redis
            String token = UUID.randomUUID().toString().replace("-", "");
            String key = MessageFormat.format(RedisUtil.RedisKeyDef.SYSTEMUSER_LOGIN_SUCCESS, token);
            redisStrUtil.set(key, loginDetailVO.toString());
            redisStrUtil.expire(key, loginExpire, TimeUnit.SECONDS);
            // map封装一下，将token传回前端
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("userInfo", loginDetailVO);
            responseDTO = ResponseUtil.success(map);
        }else{
            String key = MessageFormat.format(RedisUtil.RedisKeyDef.SYSTEMUSER_LOGIN_FAILED, loginDTO.getUserName());
            if (redisIntUtil.hasKey(key)){
                Integer fail = redisIntUtil.get(key);
                redisIntUtil.set(key, ++fail);
                redisIntUtil.expire(key, 10, TimeUnit.MINUTES);
            }
            responseDTO = ResponseUtil.error(ResponseEnum.PASSWORD_ERROR);
        }
        logger.debug("invoke login end, result: {}", responseDTO);
        return responseDTO;
    }

}
