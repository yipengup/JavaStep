package com.yipengup.chapter1.threadsleep;

/**
 * 线程有限期等待（不会释放已经持有的锁资源）
 *
 * @author yipengup
 * @date 2021/12/17
 */
public class ThreadSleep {

    private static final Object RESOURCE = new Object();

    public static void main(String[] args) {

        Thread threadOne = new Thread(() -> {
            synchronized (RESOURCE) {
                System.out.println("threadOne 开始休眠");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
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

    }
}
