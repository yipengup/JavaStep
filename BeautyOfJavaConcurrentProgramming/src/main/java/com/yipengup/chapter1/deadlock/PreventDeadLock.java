package com.yipengup.chapter1.deadlock;

/**
 * 申请资源的有序性（有效避免环路等待，请求并持有两个死锁条件）
 *
 * @author yipengup
 * @date 2021/12/17
 */
public class PreventDeadLock {
    private static final Object RESOURCE_A = new Object();
    private static final Object RESOURCE_B = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (RESOURCE_A) {
                System.out.println("threadA 持有 resourceA");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (RESOURCE_B) {
                    System.out.println("threadA 持有 resourceB");
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (RESOURCE_A) {
                System.out.println("threadB 持有 resourceA");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (RESOURCE_B) {
                    System.out.println("threadB 持有 resourceB");
                }
            }
        }).start();


    }

}
