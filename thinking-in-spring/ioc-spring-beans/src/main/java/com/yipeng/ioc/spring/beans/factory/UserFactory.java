package com.yipeng.ioc.spring.beans.factory;

import com.yipeng.ioc.spring.beans.domain.User;

/**
 * {@link com.yipeng.ioc.spring.beans.domain.User} 抽象工厂
 *
 * @author yipeng
 */
public interface UserFactory {

    User createUser();

}
