package com.yipeng.dependency.lookup.lazy;

import com.yipeng.common.domain.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 延迟依赖查找
 * {@link org.springframework.beans.factory.ObjectFactory}
 * {@link org.springframework.beans.factory.config.ObjectFactoryCreatingFactoryBean}
 * {@link org.springframework.beans.factory.ObjectProvider}
 * <p>
 * {@link org.springframework.beans.factory.support.DefaultListableBeanFactory#getBeanProvider(Class)}
 * 这里实际上还是利用的 ObjectProvide，获取到 BeanProvider 之后，调用 ObjectProvider#getObject() 的时候才会获取到依赖对象
 *
 * @author yipeng
 */
public class LazyLookupDemo {

    @Bean
    public User user() {
        return User.createUser();
    }

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(LazyLookupDemo.class);
        applicationContext.refresh();
        ObjectProvider<User> beanProvider = applicationContext.getBeanProvider(User.class);
        // org.springframework.beans.factory.support.DefaultListableBeanFactory.BeanObjectProvider#getObject()
        // 本质上还是从 IOC 容器中获取到指定类型的 Bean 信息
        User user = beanProvider.getObject();
        System.out.println("BeanProvider 延迟依赖查找 user = " + user);
        applicationContext.close();
    }

}
