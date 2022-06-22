package com.yipeng.ioc.spring.beans.gc;

/**
 * @author yipeng
 */
public class GcBean {

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Gc Bean finalize...");
    }
}
