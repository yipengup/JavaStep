package com.yipeng.ioc.spring.beans.destroy;

import org.springframework.beans.factory.DisposableBean;

import javax.annotation.PreDestroy;

/**
 * 演示 Bean 的销毁触发的方法示例
 *
 * @author yipeng
 */
public class DestroyBean implements DisposableBean {

    @PreDestroy
    public void preDestroy() {
        System.out.println("@PreDestroy");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("Disposable#destroy()");
    }

    public void destroyMethod() {
        System.out.println("destroyMethod");
    }
}
