package cn.tinet.operationplatformservice.interceptor;

import cn.tinet.operationplatformservice.utils.RedisUtil;
import cn.tinet.operationplatformservice.utils.ResultUtil;
import cn.tinet.operationplatformservice.vo.ResponseEnum;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

/**
 * @Time : 2021/3/4 22:44
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : AuthInterceptor.java
 * @Software: IntelliJ IDEA
 **/
public class AuthInterceptor implements HandlerInterceptor {

    public static final String TOKEN_NAME = "x-access-token";

    @Autowired
    private RedisUtil<String> redisUtil = null;

    @Value("${login.expire}")
    private Long loginExpire = null;

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("url: {}", request.getRequestURL().toString());
        String accesstoken = request.getHeader(TOKEN_NAME);
        if(accesstoken != null && !"".equals(accesstoken)){
            String key = MessageFormat.format(RedisUtil.RedisKeyDef.SYSTEMUSER_LOGIN_SUCCESS, accesstoken);
            if(redisUtil.hasKey(key)){
                //重置key过期时间
                redisUtil.expire(key, loginExpire, TimeUnit.SECONDS);
                return true;
            }
        }
        Gson gson = new Gson();
        returnJson(response, gson.toJson(ResultUtil.error(ResponseEnum.UNAUTHORIZED_ACCESS)));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private void returnJson(HttpServletResponse response, String json) throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);
        } catch (IOException e) {
            logger.error("response error: {}", e.getMessage());
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
