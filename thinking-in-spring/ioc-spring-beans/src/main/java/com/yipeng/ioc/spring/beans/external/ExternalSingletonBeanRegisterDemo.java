package com.yipeng.ioc.spring.beans.external;

import com.yipeng.ioc.spring.beans.domain.User;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 演示如何注册外部单例对象到 IOC 容器中，而不是通过 BeanDefinition
 * {@link org.springframework.beans.factory.config.SingletonBeanRegistry#registerSingleton(String, Object)}
 *
 * @author yipeng
 */
public class ExternalSingletonBeanRegisterDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 创建外部化对象
        User user = User.createUser();
        // 将外部化对象注册到 IOC 容器中
        // ConfigurableListableBeanFactory 继承了 SingletonBeanRegistry
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        beanFactory.registerSingleton("user", user);
        applicationContext.refresh();

        // 通过依赖查找查看是否注册成功
        User userByLookup = applicationContext.getBean("user", User.class);
        // user == userByLookup: true
        System.out.println("user == userByLookup: " + (user == userByLookup));

        applicationContext.close();
    }
}
