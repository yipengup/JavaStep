package com.yipeng.ioc.spring.beans.factory;

import com.yipeng.ioc.spring.beans.domain.User;

/**
 * @author yipeng
 */
public class ServiceLoaderUserFactory implements UserFactory {
    @Override
    public User createUser() {
        User user = new User();
        user.setId(2L);
        user.setName("serviceLoader");
        return user;
    }
}
