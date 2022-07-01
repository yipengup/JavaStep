package com.yipeng.dependency.inject.autowiring;

import com.yipeng.dependency.inject.domain.User;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 根据 Bean 的类型自动注入
 * {@link AbstractAutowireCapableBeanFactory#autowireByType(String, AbstractBeanDefinition, BeanWrapper, MutablePropertyValues)}
 * {@link org.springframework.beans.factory.annotation.Autowire#BY_TYPE}
 *
 * @author yipeng
 */
public class AutowiringByTypeDemo {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/META-INF/autowiring-bytype-context.xml");
        // 根据类型自动注入的时候，如果发现多个适合的 Bean，抛出 NoUniqueBeanDefinitionException
        // 此时可通过给 Bean 添加 Primary 属性即可
        User userByType = applicationContext.getBean("userByType", User.class);
        // userByType = User(id=1, name=Name(name=AutowiringByType))
        System.out.println("userByType = " + userByType);

    }
}
