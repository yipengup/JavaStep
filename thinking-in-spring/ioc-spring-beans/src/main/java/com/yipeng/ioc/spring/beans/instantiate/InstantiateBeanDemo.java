package com.yipeng.ioc.spring.beans.instantiate;

import com.yipeng.ioc.spring.beans.domain.User;
import com.yipeng.ioc.spring.beans.factory.UserFactoryBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 实例化 {@link com.yipeng.ioc.spring.beans.domain.User} 的几种方式
 * <p>
 * 1、构造器实例化
 * 2、静态方法实例化
 * 3、工厂方法实例化
 * 4、FactoryBean实例化
 *
 * @author yipeng
 */
public class InstantiateBeanDemo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-instantiate-context.xml");
        // 1、构造器实例化
        User instantiateUserByConstruct = applicationContext.getBean("instantiateUserByConstruct", User.class);
        System.out.println("instantiateUserByConstruct = " + instantiateUserByConstruct);

        // 2、静态方法实例化
        User instantiateUserByStaticMethod = applicationContext.getBean("instantiateUserByStaticMethod", User.class);
        System.out.println("instantiateUserByStaticMethod = " + instantiateUserByStaticMethod);

        // 3、工厂方法实例化
        User instantiateUserByFactoryMethod = applicationContext.getBean("instantiateUserByFactoryMethod", User.class);
        System.out.println("instantiateUserByFactoryMethod = " + instantiateUserByFactoryMethod);

        // 4、FactoryBean实例化（对于 FactoryBean 当调用 getBean(beanName, classType)，直接会调用 FactoryBean#getObject 获取到实际的对象）
        User instantiateUserByFactoryBean = applicationContext.getBean("instantiateUserByFactoryBean", User.class);
        System.out.println("instantiateUserByFactoryBean = " + instantiateUserByFactoryBean);

        // AbstractApplicationContext.getBean(java.lang.Class<T>) 直接会获取指定的 Bean，不会因为是 FactoryBean 而区别对待
        UserFactoryBean userFactoryBean = applicationContext.getBean(UserFactoryBean.class);
        System.out.println("userFactoryBean = " + userFactoryBean);
    }
}
