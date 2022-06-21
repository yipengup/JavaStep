package com.yipeng.ioc.spring.beans.definition.register;

import com.yipeng.ioc.spring.beans.domain.User;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 注册 通过注解定义的 BeanDefinition
 * <p>
 * 常用注解有：
 * {@link org.springframework.context.annotation.Bean}
 * {@link org.springframework.stereotype.Component} 以及其派生注解（Note: @Configuration 也是 @Component 的派生注解）
 * {@link org.springframework.context.annotation.Import} 导入某个配置类, 指示要导入的一个或多个组件类——通常是 @Configuration 类。
 *
 * @author yipeng
 */
public class AnnotationBeanDefinitionRegistryDemo {

    public static void main(String[] args) {
        // AnnotationConfigApplicationContext 实现了 BeanDefinitionRegistry
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 可传入一个或多个组件类（componentClasses
        // 会自动将组件类以及其下面的符合条件（包括 @Configuration 、@Bean 、@Component）的 BeanDefinition 都注册到容器中
        applicationContext.register(Config.class, AnnotationBean.class, AnnotationImport.class);
        applicationContext.refresh();

        // 输出容器中注册的 Bean 信息
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        // annotation_configuration、annotationBean_innerUser、importConfig、importConfig_innerUser
        System.out.println("beanFactory = " + beanFactory);
    }

    @Configuration(value = "annotation_configuration")
    public static class Config {
    }

    public static class AnnotationBean {

        @Bean(name = "annotationBean_innerUser")
        public User user() {
            return new User();
        }
    }

    @Import(ImportConfig.class)
    public static class AnnotationImport {

    }

    @Configuration("importConfig")
    public static class ImportConfig {

        @Bean(name = "importConfig_innerUser")
        public User user() {
            return new User();
        }
    }


}
