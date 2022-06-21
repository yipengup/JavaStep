package com.yipeng.ioc.spring.beans.factory;

import com.yipeng.ioc.spring.beans.domain.User;

/**
 * @author yipeng
 */
public class DefaultUserFactory implements UserFactory {

    @Override
    public User createUser() {
        User user = new User();
        user.setId(1L);
        user.setName("yipeng");
        return user;
    }
}
