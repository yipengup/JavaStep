package com.yipengup.chapter10.countdownlatch;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yipengup
 * @date 2022/1/26
 */
public class CountDownLatchTest {

    public static void main(String[] args) {
        compareJoin();
    }

    private static void compareJoin() {
        // 利用线程池提交任务，Join方法就不合适，此时让主线程等待子线程
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CountDownLatch countDownLatch = new CountDownLatch(5);
        Runnable task = () -> {
            System.out.println("=================");
            System.out.println(new Date());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
            System.out.println("=================");
        };
        for (int i = 0; i < 5; i++) {
            executorService.execute(task);
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main end");
        executorService.shutdownNow();
    }

}
