package com.yipeng.ioc.spring.beans.name.generator;

import com.yipeng.ioc.spring.beans.name.generator.domain_one.Hello;
import org.springframework.beans.factory.support.BeanDefinitionOverrideException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * {@link org.springframework.context.annotation.AnnotationBeanNameGenerator}
 * 专门为 {@link org.springframework.stereotype.Component} 及其派生注解对应 Bean 而创建的 BeanNameGenerator
 *
 * @author yipeng
 */
public class AnnotationBeanNameGeneratorDemo {

    public static void main(String[] args) {
        // 将不同包同类名的 Bean 注册到 BeanFactory
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // applicationContext 默认采用 DefaultListableBeanFactory 作为 IOC 底层容器
        // DefaultListableBeanFactory#allowBeanDefinitionOverriding 默认为 true，就是说允许 相同的 BeanName 存入 IOC 容器
        // 后添加的 Bean 会替换前面添加的 Bean
        // 此时将 allowBeanDefinitionOverriding 属性设置为 false，则相同的 BeanName 添加到容器时抛出 BeanDefinitionOverrideException
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getBeanFactory();
        beanFactory.setAllowBeanDefinitionOverriding(false);
        // 这里根据 AnnotationBeanNameGenerator 会生成相同的 BeanName，抛出 BeanDefinitionOverrideException
        try {
            applicationContext.register(Hello.class, com.yipeng.ioc.spring.beans.name.generator.domain_two.Hello.class);
        } catch (BeanDefinitionOverrideException e) {
            // Invalid bean definition with name 'hello' defined
            System.out.println(" throw BeanDefinitionOverrideException errMsg = " + e.getMessage());
        }
        applicationContext.refresh();
        System.out.println("beanFactory = " + beanFactory);
    }
}
