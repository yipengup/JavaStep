package com.yipeng.ioc.spring.beans.instantiate;

import com.yipeng.ioc.spring.beans.domain.User;
import com.yipeng.ioc.spring.beans.factory.DefaultUserFactory;
import com.yipeng.ioc.spring.beans.factory.UserFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ServiceLoader;

/**
 * 特殊实例化 Bean 的几种方式
 * 1、{@link org.springframework.beans.factory.serviceloader.ServiceLoaderFactoryBean}
 * 2、{@link org.springframework.beans.factory.config.AutowireCapableBeanFactory#createBean(Class)}
 * 3、{@link org.springframework.beans.factory.support.BeanDefinitionRegistry#registerBeanDefinition(String, BeanDefinition)}
 *
 * @author yipeng
 */
public class SpecialInstantiateBeanDemo {

    public static void main(String[] args) throws Exception {
        // 1、通过 ServiceLoaderFactoryBean 实例化 Bean 对象
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/META-INF/special-bean-instantiation-context.xml");
        // note： ServiceLoaderFactoryBean 中 FactoryBean 获取到的对象实际上是 ServiceLoader
        // ServiceLoaderFactoryBean serviceLoaderFactoryBean = applicationContext.getBean(ServiceLoaderFactoryBean.class);
        ServiceLoader<UserFactory> serviceLoader = applicationContext.getBean("instantiateUserByServiceLoaderFactoryBean", ServiceLoader.class);
        for (UserFactory userFactory : serviceLoader) {
            User user = userFactory.createUser();
            System.out.println("ServiceLoaderFactoryBean user = " + user);
        }
        // 2、通过 AutowireCapableBeanFactory#createBean 实例化 Bean 对象
        AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
        // note：实例化的 Bean 对象不会添加到容器里面
        DefaultUserFactory userFactory = beanFactory.createBean(DefaultUserFactory.class);
        User user = userFactory.createUser();
        System.out.println("AutowireCapableBeanFactory user = " + user);

        // 关闭容器
        applicationContext.close();
    }

}
