package com.yipeng.ioc.spring.beans.service.loader;

import com.yipeng.ioc.spring.beans.domain.User;
import com.yipeng.ioc.spring.beans.factory.UserFactory;

import java.util.ServiceLoader;

/**
 * SPI {@link java.util.ServiceLoader} 示例
 * 可以在 classpath:/META-INF/services 下面创建文件名为接口全路径的文件，文件内容是接口的实现类的全路径列表
 *
 * @author yipeng
 */
public class ServiceLoaderDemo {

    public static void main(String[] args) {
        // 返回的是指定的接口的实现类的迭代器
        ServiceLoader<UserFactory> serviceLoader = ServiceLoader.load(UserFactory.class);
        for (UserFactory userFactory : serviceLoader) {
            User user = userFactory.createUser();
            System.out.println("user = " + user);
        }
    }
}
