package cn.tinet.operationplatformservice.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Time : 2021/3/4 23:35
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : WebConfigurer.java
 * @Software: IntelliJ IDEA
 **/
@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/session/login")
                .excludePathPatterns("/app/replicasets");
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedHeaders("*")
//                .allowedMethods("*")
//                .allowedOrigins("http://localhost")
//                .allowCredentials(true);
//    }
}
