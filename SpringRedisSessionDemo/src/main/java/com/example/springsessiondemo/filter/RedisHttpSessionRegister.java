package com.example.springsessiondemo.filter;

import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注册 RedisHttpSessionInterceptor
 *
 * @author yipengup
 * @date 2021/9/25
 */
// @Component
public class RedisHttpSessionRegister implements WebMvcConfigurer {

    /**
     * 拦截的路径 默认拦截所有的请求
     */
    public static final String INTERCEPTOR_PATH = "/**";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new RedisHttpSessionInterceptor());
        registration.addPathPatterns(INTERCEPTOR_PATH);
        // 添加不拦截的路径
        registration.excludePathPatterns("/user/login");
    }
}
