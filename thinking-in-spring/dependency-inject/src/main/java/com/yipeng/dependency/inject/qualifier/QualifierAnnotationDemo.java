package com.yipeng.dependency.inject.qualifier;

import com.yipeng.common.domain.User;
import com.yipeng.dependency.inject.qualifier.annotation.QualifierExtended;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.Collection;

/**
 * {@link org.springframework.beans.factory.annotation.Qualifier} 注解示例
 * <p>
 * {@link Qualifier} 注解的作用有三个
 * 1、自动装配时，可以在字段或者参数上使用此注释作为候选 Bean 的限定符。它会根据 {@link Qualifier#value()} 去获取到指定的 Bean Name 的 Bean。
 * 在指定了 {@link Qualifier} 注解之后，如果根据名称找不到指定的 Bean，直接会抛出异常
 * {@link org.springframework.beans.factory.UnsatisfiedDependencyException} 、{@link org.springframework.beans.factory.NoSuchBeanDefinitionException}
 * <p>
 * 2、通过给 Bean 指定分组信息，自动装配时，可以获取指定分组下的 Bean。
 * 3、通过自定义注解扩展 {@link Qualifier} 功能，实现自定义分组的功能。
 *
 * @author yipeng
 */
public class QualifierAnnotationDemo {

    @Autowired
    private User user;

    @Autowired
    @Qualifier("user1")
    private User user2;

    // 未指定限定符就可以获取到所有分组的 User Bean
    @Autowired
    private Collection<User> defaultGroupUser;

    // 通过限定符指定只获取到指定分组的 User Bean
    @Autowired
    @Qualifier("user_qualifier")
    private Collection<User> qualifierGroupUser;

    @Autowired
    @QualifierExtended
    private Collection<User> qualifierExtendedGroupUser;

    @Bean
    public User user1() {
        return User.createUser("user1");
    }

    @Bean
    @Primary
    public User user2() {
        return User.createUser("user2");
    }

    // 通过限定符指定当前 Bean 的分组信息，在进行依赖注入的时候，要是没有指定分组信息是获取不到当前分组的 Bean 的信息的
    @Bean
    @Qualifier("user_qualifier")
    public User user3() {
        return User.createUser("user3");
    }

    @Bean
    @Qualifier("user_qualifier")
    public User user4() {
        return User.createUser("user4");
    }

    @Bean
    @Qualifier
    public User user5() {
        return User.createUser("user5");
    }

    @Bean
    @QualifierExtended
    public User user6() {
        return User.createUser("user6");
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(QualifierAnnotationDemo.class);
        applicationContext.refresh();

        QualifierAnnotationDemo demo = applicationContext.getBean(QualifierAnnotationDemo.class);
        System.out.println("user = " + demo.user);
        System.out.println("user2 = " + demo.user2);
        System.out.println("defaultGroupUser = " + demo.defaultGroupUser);
        // qualifierGroupUser = [User(id=3, name=user3), User(id=4, name=user4)]
        System.out.println("qualifierGroupUser = " + demo.qualifierGroupUser);
        // qualifierExtendedGroupUser = [User(id=6, name=user6)]
        System.out.println("qualifierExtendedGroupUser = " + demo.qualifierExtendedGroupUser);


        applicationContext.close();
    }


}
