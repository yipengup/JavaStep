package com.yipeng.dependency.inject.method;

import com.yipeng.common.domain.User;
import com.yipeng.common.domain.UserHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 利用 {@link org.springframework.context.annotation.Bean} 注解进行依赖注入示例
 * 自动注入的规则 {@link com.yipeng.dependency.inject.field.FieldInjectDemo}
 *
 * @author yipeng
 */
public class BeanInjectionDemo {

    // 默认 {@Bean} 注解的 Bean 的 id 取得是方法名称
    // 可通过 {@Bean#value() 或者 @Bean#name()} 自定义 Bean 的 id
    @Bean
    public User user() {
        return User.createUser("userByBeanAnnotation");
    }

    @Bean
    public User userParam() {
        return User.createUser("userParam");
    }

    // 查看对于 {@Bean} 注入的 Bean 中对应的方法参数中查找依赖的方法，符合自动注入的规则
    // 方法即使是非 public 也能被识别
    @Bean
    private UserHolder userHolder(User userParam) {
        UserHolder userHolder = new UserHolder();
        userHolder.setUser(userParam);
        return userHolder;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanInjectionDemo.class);
        applicationContext.refresh();

        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        System.out.println("beanFactory = " + beanFactory);

        UserHolder userHolder = beanFactory.getBean(UserHolder.class);
        // userHolder = UserHolder(user=User(id=2, name=userParam))
        System.out.println("userHolder = " + userHolder);

        applicationContext.close();
    }
}
