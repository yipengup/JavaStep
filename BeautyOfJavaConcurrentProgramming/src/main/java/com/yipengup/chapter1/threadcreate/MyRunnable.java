package com.yipengup.chapter1.threadcreate;

/**
 * 创建线程的方式2：实现Runnable接口
 * <p>
 * 优点：
 * 1、Runnable 就是线程需要执行的任务。Runnable可以被多个线程使用， 多个线程执行的任务是一样的。
 * <p>
 * 缺点：
 * 1、没有返回值
 *
 * @author yipengup
 * @date 2021/12/11
 */
public class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("MyRunnable.run");
    }

    public static void main(String[] args) {
        // runnable任务可以被多次使用
        MyRunnable task = new MyRunnable();
        new Thread(task).start();
        new Thread(task).start();
    }
}
