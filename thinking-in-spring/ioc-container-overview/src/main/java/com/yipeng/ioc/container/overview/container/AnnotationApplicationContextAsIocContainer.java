package com.yipeng.ioc.container.overview.container;

import com.yipeng.ioc.container.overview.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 注解能力 {@link org.springframework.context.ApplicationContext} 作为 IOC 容器
 * @author yipeng
 */
public class AnnotationApplicationContextAsIocContainer {

    public static void main(String[] args) {

        // 获取到注解类型的 ApplicationContext
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类作为配置类注册到 ApplicationContext，从而可以加载到当前类中配置的 Bean 信息
        applicationContext.register(AnnotationApplicationContextAsIocContainer.class);
        // 调用完 register 方法之后一定要调用 refresh 方法
        applicationContext.refresh();

        // 获取已经加载的 Bean 的信息
        User user = applicationContext.getBean(User.class);
        System.out.println("user = " + user);

        // 完成之后调用 close 方法，销毁所有的 Bean 信息
        applicationContext.close();
    }

    @Bean
    public User user() {
        User user = new User();
        user.setId("1");
        user.setName("yipeng");
        return user;
    }


}
