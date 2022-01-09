package com.yipengup.chapter3;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author yipengup
 * @date 2021/12/29
 */
public class ThreadLocalRandomTest {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                int nextInt = ThreadLocalRandom.current().nextInt();
                System.out.println("threadName = " + Thread.currentThread().getName() + ", nextInt = " + nextInt);
            }).start();
        }
        // 让主线程等待子线程执行完毕
        while (Thread.activeCount() > 2) {
            System.out.println(Thread.activeCount());
            Thread.yield();
        }
        long end = System.currentTimeMillis() - start;
        // end = 1075
        System.out.println("end = " + end);
    }
}
