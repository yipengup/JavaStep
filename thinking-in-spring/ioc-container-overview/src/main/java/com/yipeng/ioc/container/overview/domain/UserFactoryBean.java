package com.yipeng.ioc.container.overview.domain;

import org.springframework.beans.factory.FactoryBean;

import java.util.concurrent.atomic.AtomicLong;

/**
 * FactoryBean 可以简单理解为 工厂 + Bean 的实现。
 * <p>
 * 它的主要作用就是为了创建和管理复杂Bean，一般通过XML或者注解的配置都是一些简单的Bean，直接设置相关的属性即可
 * FactoryBean 就可以帮助我们创建出一系列复杂逻辑的Bean
 *
 * @author yipeng
 */
public class UserFactoryBean implements FactoryBean<User> {

    private static final AtomicLong USER_ID = new AtomicLong();

    @Override
    public User getObject() throws Exception {
        User user = new User();
        user.setId(String.valueOf(USER_ID.incrementAndGet()));
        user.setName("yipeng");
        return user;
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}
