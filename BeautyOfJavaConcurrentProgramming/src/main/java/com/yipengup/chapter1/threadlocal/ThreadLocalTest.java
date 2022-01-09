package com.yipengup.chapter1.threadlocal;

/**
 * @author yipengup
 * @date 2021/12/17
 */
public class ThreadLocalTest {

    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            THREAD_LOCAL.set("threadA localVariable");
            THREAD_LOCAL.set("threadA localVariable1");
            System.out.println("threadA: " + THREAD_LOCAL.get());
            THREAD_LOCAL.remove();
            System.out.println("threadA: remove after : " + THREAD_LOCAL.get());
        });
        threadA.start();

        Thread threadB = new Thread(() -> {
            THREAD_LOCAL.set("threadB localVariable");
            System.out.println("threadB: " + THREAD_LOCAL.get());
            THREAD_LOCAL.remove();
            System.out.println("threadB: remove after : " + THREAD_LOCAL.get());
        });
        threadB.start();


    }

}
