package com.yipengup.chapter1.threadcreate;

/**
 * 创建线程方式一： 继承Thread
 * <p>
 * 优点：
 * 1、每个线程都是单一的对象，可以给自定义线程对象， 设置相关属性
 * <p>
 * 缺点：
 * 1、单继承
 * 2、没有返回值
 *
 * @author yipengup
 * @date 2021/12/11
 */
public class MyThread extends Thread {

    @Override
    public void run() {
        System.out.println("MyThread.run");
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();
    }
}
