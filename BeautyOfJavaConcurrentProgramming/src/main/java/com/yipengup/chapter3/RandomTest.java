package com.yipengup.chapter3;

import java.util.Random;

/**
 * @author yipengup
 * @date 2021/12/29
 */
public class RandomTest {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                int nextInt = random.nextInt();
                System.out.println("threadName = " + Thread.currentThread().getName() + ", nextInt = " + nextInt);
            }).start();
        }
        // 让主线程等待子线程执行完毕
        while (Thread.activeCount() > 2) {
            System.out.println(Thread.activeCount());
            Thread.yield();
        }
        long end = System.currentTimeMillis() - start;
        // end = 1264
        System.out.println("end = " + end);
    }
}
