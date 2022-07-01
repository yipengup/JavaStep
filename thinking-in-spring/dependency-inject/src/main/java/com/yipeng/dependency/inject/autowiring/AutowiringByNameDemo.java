package com.yipeng.dependency.inject.autowiring;

import com.yipeng.dependency.inject.domain.User;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 根据 Bean 的名称自动注入示例
 * <p>
 * {@link AbstractAutowireCapableBeanFactory#autowireByName(String, AbstractBeanDefinition, BeanWrapper, MutablePropertyValues)}
 * {@link org.springframework.beans.factory.annotation.Autowire#BY_NAME}
 *
 * @author yipeng
 */
public class AutowiringByNameDemo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/META-INF/autowiring-byname-context.xml");
        User userByName = applicationContext.getBean("userByName", User.class);
        // userByName = User(id=1, name=Name(name=yipeng))
        System.out.println("userByName = " + userByName);
    }
}
