package com.yipengup.chapter10.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author yipengup
 * @date 2022/1/26
 */
public class SemaphoreTest {

    public static void main(String[] args) throws InterruptedException {
        // 初始化持有0个信号量，并且采用非公平的策略
        Semaphore semaphore = new Semaphore(0, false);
        Runnable task = () -> {
            // 线程都生成一个信号量
            System.out.println(Thread.currentThread() + " release");
            semaphore.release();
        };

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 2; i++) {
            executorService.execute(task);
        }
        // 表明当前线程需要消费2个信号量，否则加入到阻塞队列
        semaphore.acquire(2);
        System.out.println("main end");
        executorService.shutdown();
    }
}
