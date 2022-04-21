package com.example.springbootknife4jdemo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import javax.servlet.MultipartConfigElement;

/**
 * @author yipengup
 * @date 2021/9/28
 */
@Configuration
public class ServletConfig {

    @Bean
    @Qualifier("backend_servlet")
    public ServletRegistrationBean<DispatcherServlet> backendServlet(MultipartConfigElement multipartConfigElement) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(BackendWebMvcConfig.class);
        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);

        ServletRegistrationBean<DispatcherServlet> servletRegistrationBean = new ServletRegistrationBean<>(dispatcherServlet);
        servletRegistrationBean.addUrlMappings("/v1/backend/*");
        servletRegistrationBean.setLoadOnStartup(1);
        // 注意，这里不能设置名称， 就是要用该ServletRegistrationBean注册的dispatcherServlet替换掉默认的servletRegistrationBean
        // 这样才能统一加前缀才能访问的效果， 不然还是可以通过不加前缀进行访问
        // servletRegistrationBean.setName("backend");
        // 支持 form-data
        servletRegistrationBean.setMultipartConfig(multipartConfigElement);
        return servletRegistrationBean;
    }

    @Bean
    @Qualifier("app_servlet")
    public ServletRegistrationBean<DispatcherServlet> appServlet(MultipartConfigElement multipartConfigElement) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppWebMvcConfig.class);
        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);

        ServletRegistrationBean<DispatcherServlet> servletRegistrationBean = new ServletRegistrationBean<>(dispatcherServlet);
        servletRegistrationBean.addUrlMappings("/v1/app/*");
        servletRegistrationBean.setLoadOnStartup(1);
        // 这里将其注册成app，专门处理app模块的下的请求
        servletRegistrationBean.setName("app");
        // 支持 form-data
        servletRegistrationBean.setMultipartConfig(multipartConfigElement);
        return servletRegistrationBean;
    }

}
