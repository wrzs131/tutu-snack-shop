package com.itheima.config;

import com.itheima.interceptor.JwtTokenUserInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private JwtTokenUserInterceptor jwtTokenUserInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(jwtTokenUserInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/user/login",
                        "/doc.html",
                        "/webjars/**",
                        "/swagger-resources",
                        "/v3/api-docs/**"
                );
        log.info("自定义拦截器注册完成");
    }
}
