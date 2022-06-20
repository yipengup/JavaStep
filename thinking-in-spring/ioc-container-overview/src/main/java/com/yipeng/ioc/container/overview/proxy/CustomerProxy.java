package com.yipeng.ioc.container.overview.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 自定义 JDK 动态代理
 *
 * @author yipeng
 */
public class CustomerProxy {

    /**
     * 被代理对象
     */
    private final Object object;

    public CustomerProxy(Object object) {
        this.object = object;
    }

    /**
     * @return 获取到代理对象
     */
    public Object newInstance() {

        // 通过代理获取到被代理的实例对象
        return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                method.setAccessible(true);
                String methodName = method.getName();
                System.out.println("执行代理方法[" + methodName + "]前逻辑============");
                Object o = method.invoke(object, args);
                System.out.println("执行代理方法[" + methodName + "]后逻辑============");
                return o;
            }
        });
    }

}
