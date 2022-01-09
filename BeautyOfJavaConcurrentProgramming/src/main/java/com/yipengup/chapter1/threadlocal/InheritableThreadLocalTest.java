package com.yipengup.chapter1.threadlocal;

/**
 * InheritableThreadLocal 子线程会继承父线程的本地副本
 *
 * @author yipengup
 * @date 2021/12/22
 */
public class InheritableThreadLocalTest {

    private static final ThreadLocal<String> THREAD_LOCAL = new InheritableThreadLocal<>();

    public static void main(String[] args) {


        THREAD_LOCAL.set("main thread");

        Thread childThread = new Thread(() -> {
            String s = THREAD_LOCAL.get();
            System.out.println("s = " + s);
        });

        childThread.setName("child thread");
        childThread.start();

        System.out.println("main thread end");
    }

}
