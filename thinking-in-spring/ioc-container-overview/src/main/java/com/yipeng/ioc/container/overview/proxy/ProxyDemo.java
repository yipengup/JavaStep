package com.yipeng.ioc.container.overview.proxy;

import com.yipeng.ioc.container.overview.domain.User;

/**
 * JDK 动态代理示例
 *
 * @author yipeng
 */
public class ProxyDemo {

    public static void main(String[] args) {
        User user = new User();
        System.out.println("user = " + user);
        CustomerProxy proxy = new CustomerProxy(user);
        Object proxyInstance = proxy.newInstance();
        System.out.println("proxyInstance = " + proxyInstance);
    }
}
