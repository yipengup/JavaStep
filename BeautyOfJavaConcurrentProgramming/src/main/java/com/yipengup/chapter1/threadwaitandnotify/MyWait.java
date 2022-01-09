package com.yipengup.chapter1.threadwaitandnotify;

import java.util.Date;

/**
 * @author yipengup
 * @date 2021/12/14
 */
public class MyWait {

    // 共享变量
    public static final Integer SHARE = 1;

    private static volatile boolean waitCondition = true;

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            synchronized (SHARE) {
                System.out.println(new Date() + ": wait pre");
                try {
                    // 当前线程释放对象锁，然后无限期挂起等待，等待其他线程显示唤醒
                    // 结束挂起等待的方式： 1、其他线程调用共享对象的 notify、notifyAll方法 2、其他线程调用了该线程的 interrupt 方法
                    // 防止被虚假唤醒，这里采用条件控制住
                    while (waitCondition) {
                        SHARE.wait();
                    }
                } catch (InterruptedException e) {
                    System.out.println(new Date() + ": interrupt");
                }
                System.out.println(new Date() + ": wait post");
            }
        });
        thread.start();

        new Thread(() -> {
            try {
                // 当前线程不会释放对象锁，有限期等待，等待指定的时间后处于就绪状态
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (SHARE) {
                System.out.println(new Date() + ": notify all pre");
                // 通知线程结束等待，等待被调度
                SHARE.notifyAll();
                waitCondition = false;
                System.out.println(new Date() + ": notify all post");
            }
        }).start();

        // new Thread(() -> {
        //     try {
        //         // 当前线程不会释放对象锁，有限期等待，等待指定的时间后处于就绪状态
        //         Thread.sleep(1000);
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        //     System.out.println(new Date() + ": interrupt pre");
        //     thread.interrupt();
        //     waitCondition = false;
        //     System.out.println(new Date() + ": interrupt post");
        // }).start();

        System.out.println(new Date() + "main end");
    }


}
