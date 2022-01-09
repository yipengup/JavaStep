package com.yipengup.chapter1.threadlocal;

/**
 * ThreadLocal 不会继承父线程中的副本
 *
 * @author yipengup
 * @date 2021/12/22
 */
public class ThreadLocalNonInheritable {

    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public static void main(String[] args) {


        THREAD_LOCAL.set("main thread");

        new Thread(() -> {
            String s = THREAD_LOCAL.get();
            System.out.println("s = " + s);
        }).start();

        System.out.println("main thread end");
    }


}
