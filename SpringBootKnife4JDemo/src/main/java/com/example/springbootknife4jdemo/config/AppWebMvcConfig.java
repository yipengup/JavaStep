package com.example.springbootknife4jdemo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * EnableWebMvc 用于表示该类扫描对应的包
 *
 * @author yipengup
 * @date 2021/9/28
 */
@Component
@EnableWebMvc
@ComponentScan(
        basePackages = {"com.example.springbootknife4jdemo.web.controller.app", "springfox.documentation.swagger.web"}
)
public class AppWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
