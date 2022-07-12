package com.yipeng.dependency.inject.field;

import com.yipeng.common.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;

/**
 * 字段注入示例
 * <p>
 * 字段注入只提供手动通过注解的方式注入，可用的注解有：
 * {@link org.springframework.beans.factory.annotation.Autowired}
 * {@link javax.annotation.Resource}
 * {@link javax.inject.Inject} note：需要引入 JSR-330
 * <p>
 * note:
 * {@link Autowired} 注入的字段，先判断字符对应的类型的 Bean 是否有 {@link Primary} 注解修饰，有的话直接返回，
 * 没有的话根据字段的名称在 Bean 容器中查找注入，若是找不到再通过字段的类型在 Bean 容器中查找注入。
 * {@link Resource} 注入的字段，通过 {@link Resource#name()} 指定在 Bean 容器中查找注入的名称， name 默认为属性名
 * <p>
 * 字段注入会忽略掉 static 修饰的类变量
 *
 * @author yipeng
 */
public class FieldInjectDemo {

    // 通过字段注入，查看 @AutoWired 注解是依赖 Bean id 去注入还是依赖 bean type 去注入
    // @Autowired 会结合名称+类型进行依赖查找后注入
    @Autowired
    private User user;

    // @resource 可指定在容器中查找 Bean 的 id，若是没有指定 name，默认就是属性名称
    // @Resource 只能通过名称进行依赖查找后注入
    @Resource(name = "user")
    private User user1;

    // 设置 Bean 的 id 为 user
    @Bean("user")
    public User user() {
        return User.createUser("userByName");
    }

    // 设置 Bean 的 id 为 userByType
    @Bean("userByType")
    public User userByType() {
        return User.createUser("userByType");
    }

    @Bean
    @Primary
    public User userPrimary() {
        return User.createUser("userByPrimary");
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(FieldInjectDemo.class);
        applicationContext.refresh();

        FieldInjectDemo demo = applicationContext.getBean(FieldInjectDemo.class);
        User userField = demo.user;
        System.out.println("userField = " + userField);

        User resourceUser = demo.user1;
        System.out.println("resourceUser = " + resourceUser);

        applicationContext.close();
    }
}
