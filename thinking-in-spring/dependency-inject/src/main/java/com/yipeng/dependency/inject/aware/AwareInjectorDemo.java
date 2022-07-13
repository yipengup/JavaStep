package com.yipeng.dependency.inject.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 通过实现 {@link org.springframework.beans.factory.Aware} 子接口，通过回调的方式注入相关 Bean
 * {@link org.springframework.beans.factory.BeanFactoryAware} 获取到 IOC 容器 BeanFactory
 * {@link org.springframework.context.ApplicationContextAware} 获取到 Spring 应用上下文 ApplicationContext 对象
 * {@link org.springframework.context.EnvironmentAware} 获取到 Environment 对象
 * {@link org.springframework.beans.factory.BeanNameAware} 获取当前 Bean 的名称
 * {@link org.springframework.beans.factory.BeanClassLoaderAware} 获取到加载当前 Bean Class 的 ClassLoader
 * {@link org.springframework.context.ResourceLoaderAware} 获取资源加载器对象 ResourceLoader
 * {@link org.springframework.context.MessageSourceAware} 获取 MessageSource 对象，用于 Spring 国际化
 * {@link org.springframework.context.ApplicationEventPublisherAware} 获取 ApplicationEventPublisher 对象，用于 Spring 事件
 * {@link org.springframework.context.EmbeddedValueResolverAware} 获取 StringValueResolver 对象，用于占位符处理
 * <p>
 * 实现方式：
 * 获取到实现了对应的接口的实现类，然后用回调的方式去调用对应的方法
 *
 * @author yipeng
 */
public class AwareInjectorDemo implements BeanFactoryAware, ApplicationContextAware {

    private static ApplicationContext applicationContext;
    private static BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        AwareInjectorDemo.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AwareInjectorDemo.applicationContext = applicationContext;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AwareInjectorDemo.class);
        context.refresh();

        // true
        System.out.println(" context.getBeanFactory() == beanFactory:" + (context.getBeanFactory() == AwareInjectorDemo.beanFactory));
        // true
        System.out.println(" context == applicationContext:" + (context == AwareInjectorDemo.applicationContext));

        context.close();
    }
}
