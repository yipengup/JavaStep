package com.yipeng.ioc.spring.beans.factory;

import com.yipeng.ioc.spring.beans.domain.User;
import org.springframework.beans.factory.FactoryBean;

/**
 * {@link User} {@link FactoryBean} 实现
 *
 * @author yipeng
 */
public class UserFactoryBean implements FactoryBean<User> {

    @Override
    public User getObject() throws Exception {
        return User.createUser();
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}
