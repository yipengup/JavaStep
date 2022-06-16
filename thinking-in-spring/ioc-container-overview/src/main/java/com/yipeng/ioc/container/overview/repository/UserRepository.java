package com.yipeng.ioc.container.overview.repository;

import com.yipeng.ioc.container.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;

import java.util.Collection;


/**
 * 用户信息仓库
 *
 * @author yipeng
 */
public class UserRepository {

    private Collection<User> userCollections;

    private BeanFactory beanFactory;

    private ObjectFactory<ApplicationContext> contextObjectFactory;


    public Collection<User> getUserCollections() {
        return userCollections;
    }

    public void setUserCollections(Collection<User> userCollections) {
        this.userCollections = userCollections;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public ObjectFactory<ApplicationContext> getContextObjectFactory() {
        return contextObjectFactory;
    }

    public void setContextObjectFactory(ObjectFactory<ApplicationContext> contextObjectFactory) {
        this.contextObjectFactory = contextObjectFactory;
    }
}
