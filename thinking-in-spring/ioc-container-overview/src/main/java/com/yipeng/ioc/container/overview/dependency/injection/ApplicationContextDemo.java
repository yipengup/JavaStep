package com.yipeng.ioc.container.overview.dependency.injection;

import com.yipeng.ioc.container.overview.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.AbstractRefreshableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 校验 ApplicationContext 如何实现 IOC 容器
 * @author yipeng
 */
public class ApplicationContextDemo {

    public static void main(String[] args) {
        AbstractRefreshableApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        System.out.println("applicationContext beanFactory = " + beanFactory);

        UserRepository userRepository = applicationContext.getBean(UserRepository.class);
        BeanFactory repositoryBeanFactory = userRepository.getBeanFactory();
        System.out.println("自动注入 repositoryBeanFactory = " + repositoryBeanFactory);

        // 判断自动注入的 BeanFactory 和 ApplicationContext 代理的 BeanFactory是否是同一个对象
        // 最后发现确实是同一个对象
        System.out.println("beanFactory == repositoryBeanFactory: " + (beanFactory == repositoryBeanFactory));
    }
}
