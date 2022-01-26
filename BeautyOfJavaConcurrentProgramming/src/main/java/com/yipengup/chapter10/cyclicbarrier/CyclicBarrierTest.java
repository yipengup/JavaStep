package com.yipengup.chapter10.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author yipengup
 * @date 2022/1/26
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        // replaceJoin();

        taskDecompose();
    }

    /**
     * 利用 CyclicBarrier 实现Join功能
     */
    private static void replaceJoin() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
            // 所有的任务都执行完
            System.out.println(Thread.currentThread() + "task end!");
        });

        Runnable task = () -> {
            System.out.println(Thread.currentThread() + " barrier pre。");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + " barrier end。");
        };

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 2; i++) {
            executorService.execute(task);
        }

        // 当前主线程也需要阻塞
        System.out.println(Thread.currentThread() + " barrier pre。");
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread() + " barrier end。");


        executorService.shutdown();
    }

    /**
     * 利用 CyclicBarrier 实现任务分解
     */
    private static AtomicLong result = new AtomicLong(0L);

    private static void taskDecompose() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
            System.out.println("当前任务的结果为 result = " + result.get());
        });

        Runnable task = () -> {
            result.incrementAndGet();
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 2; i++) {
            executorService.execute(task);
        }
        // 任务提交之后关闭线程池，处于shutdown的线程池还是会执行完任务
        executorService.shutdown();
    }


}
