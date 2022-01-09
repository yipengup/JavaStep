package com.yipengup.chapter1.threadwaitandnotify;

/**
 * 双重锁造成的死锁
 *
 * @author yipengup
 * @date 2021/12/16
 */
public class DeadLockByDoubleLock {

    private static final Object RESOURCE_A = new Object();
    private static final Object RESOURCE_B = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread threadA = new Thread(() -> {
            synchronized (RESOURCE_A) {
                System.out.println("threadA 获取到 resourceA Lock");
                synchronized (RESOURCE_B) {
                    System.out.println("threadA 获取到 resourceB Lock");
                    try {
                        System.out.println("threadA 释放 resourceA Lock");
                        RESOURCE_A.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        threadA.setName("threadA");

        Thread threadB = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (RESOURCE_A) {
                System.out.println("threadB 获取到 resourceA Lock");
                synchronized (RESOURCE_B) {
                    System.out.println("threadB 获取到 resourceB Lock");
                    System.out.println("threadB 释放 resourceA Lock");
                    try {
                        RESOURCE_A.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        threadB.setName("threadB");

        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();

        System.out.println("main end");

    }


}
