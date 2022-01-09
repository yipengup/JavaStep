package com.yipengup.chapter1.deadlock;

/**
 * @author yipengup
 * @date 2021/12/17
 */
public class DeadLock {
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
            synchronized (RESOURCE_B) {
                System.out.println("threadB 持有 resourceB");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (RESOURCE_A) {
                    System.out.println("threadB 持有 resourceA");
                }
            }
        }).start();


    }

}
