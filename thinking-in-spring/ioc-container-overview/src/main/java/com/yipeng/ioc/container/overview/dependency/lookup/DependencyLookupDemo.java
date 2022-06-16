package com.yipeng.ioc.container.overview.dependency.lookup;

import com.yipeng.ioc.container.overview.annotaion.Super;
import com.yipeng.ioc.container.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * 依赖查找 示例
 * @author yipeng
 */
public class DependencyLookupDemo {

    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-lookup-context.xml");
        // 按照名称查找
        lookupInRealTime(beanFactory);
        lookupInLazyTime(beanFactory);

        // 按照类型查找
        lookupByType(beanFactory);
        // 按照类型查找组合Bean（获取到某一个类型所有的Bean信息）
        lookupCollectionByType(beanFactory);

        // 通过注解获取到注解对应的所有的 Bean 信息
        lookupCollectionByAnnotationType(beanFactory);
    }

    private static void lookupCollectionByAnnotationType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            // 获取到 BeanFactory 中所有标记为 Super 注解的Bean信息
            Map<String, Object> beans = listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println("注解查找 beans = " + beans);

        }
    }

    private static void lookupCollectionByType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            // 根据类型查找到所有的类型为 User 的Bean，此时 （key => bean id, value => bean ）
            Map<String, User> userMap = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("类型查找 userMap = " + userMap);
        }
    }

    private static void lookupByType(BeanFactory beanFactory) {
        // 类型查找单个Bean
        // note: 当 beanFactory 中存在多个 User 类型的 Bean 时，此时不知道返回哪一个就会报错，
        // 可通过给Bean标记主次（primary），此时会返回标记 primary 的Bean
        User user = beanFactory.getBean(User.class);
        System.out.println("类型查找 user = " + user);
    }

    private static void lookupInLazyTime(BeanFactory beanFactory) {
        ObjectFactory<User> objectFactory = (ObjectFactory<User>) beanFactory.getBean("objectFactory");
        // 此时才会去查找 Bean 信息，getObject实际上就是 this.beanFactory.getBean(this.targetBeanName)
        User user = objectFactory.getObject();
        System.out.println("延时名称查找 user = " + user);
    }

    private static void lookupInRealTime(BeanFactory beanFactory) {
        User user = (User) beanFactory.getBean("user");
        System.out.println("实时名称查找 user = " + user);
    }

}
