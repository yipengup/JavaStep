package com.yipengup.chapter1.threadsleep;

/**
 * 线程有限期等待（不会释放已经持有的锁资源）
 *
 * @author yipengup
 * @date 2021/12/17
 */
public class ThreadSleepInterrupt {

    private static final Object RESOURCE = new Object();

    public static void main(String[] args) {

        Thread threadOne = new Thread(() -> {
            synchronized (RESOURCE) {
                System.out.println("threadOne 开始休眠");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    // 线程被中断后，结束睡眠，处于就绪状态等待CPU调度
                    System.out.println("threadOne 被中断");
                    e.printStackTrace();
                }
                System.out.println("threadOne 执行完毕");
            }
        });

        threadOne.setName("threadOne");

        Thread threadTwo = new Thread(() -> {
            synchronized (RESOURCE) {
                System.out.println("threadTwo 开始休眠");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println("threadTwo 被中断");
                    e.printStackTrace();
                }
                System.out.println("threadTwo 执行完毕");
            }
        });

        threadTwo.setName("threadTwo");

        threadOne.start();
        threadTwo.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 主线程休眠2s后中断线程1
        threadOne.interrupt();
    }
}
