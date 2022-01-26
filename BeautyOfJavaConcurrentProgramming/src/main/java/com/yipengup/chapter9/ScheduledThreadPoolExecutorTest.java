package com.yipengup.chapter9;

import com.yipengup.chapter8.NamedThreadFactory;

import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 按照一定计划执行任务的线程池相关案例
 *
 * @author yipengup
 * @date 2022/1/25
 */
public class ScheduledThreadPoolExecutorTest {

    public static void main(String[] args) {
        // executeTask();
        // executeFixedDelayTask();
        executeFixedRateTask();
    }

    /**
     * 执行一次性延迟任务
     */
    private static void executeTask() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1,
                NamedThreadFactory.create("executeTask"), new ThreadPoolExecutor.DiscardPolicy());

        Runnable task = () -> {
            System.out.println("============================");
            System.out.println(new Date() + ":" + Thread.currentThread().getName());
            System.out.println("task count = " + scheduledThreadPoolExecutor.getTaskCount());
            System.out.println("completed task count = " + scheduledThreadPoolExecutor.getCompletedTaskCount());
            System.out.println("active thread = " + scheduledThreadPoolExecutor.getActiveCount());
            System.out.println("============================");
        };

        System.out.println("exec pre time = " + new Date());

        scheduledThreadPoolExecutor.schedule(task, 10, TimeUnit.SECONDS);
    }

    /**
     * 执行固定延迟任务
     * <p>
     * 在上次任务执行完之后固定多久执行下次任务
     */
    private static void executeFixedDelayTask() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1,
                NamedThreadFactory.create("executeFixDelayTask"), new ThreadPoolExecutor.DiscardPolicy());

        Runnable task = () -> {
            System.out.println("============================");
            System.out.println(new Date() + ":" + Thread.currentThread().getName());
            System.out.println("task count = " + scheduledThreadPoolExecutor.getTaskCount());
            System.out.println("completed task count = " + scheduledThreadPoolExecutor.getCompletedTaskCount());
            System.out.println("active thread = " + scheduledThreadPoolExecutor.getActiveCount());
            // 模拟当前处理任务需要消耗一定的时间
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("============================");
        };

        System.out.println("exec pre time = " + new Date());

        scheduledThreadPoolExecutor.scheduleWithFixedDelay(task, 5L, 2L, TimeUnit.SECONDS);
    }

    /**
     * 执行固定频率任务
     * 在上一次延迟的时间上延迟指定的时间，与当前时间无关。 可能上一个任务刚执行完，下一个任务立即执行。
     */
    private static void executeFixedRateTask() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1,
                NamedThreadFactory.create("executeFixDelayTask"), new ThreadPoolExecutor.DiscardPolicy());

        Runnable task = () -> {
            System.out.println("============================");
            System.out.println(new Date() + ":" + Thread.currentThread().getName());
            System.out.println("task count = " + scheduledThreadPoolExecutor.getTaskCount());
            System.out.println("completed task count = " + scheduledThreadPoolExecutor.getCompletedTaskCount());
            System.out.println("active thread = " + scheduledThreadPoolExecutor.getActiveCount());
            // 模拟当前处理任务需要消耗一定的时间
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("============================");
        };

        System.out.println("exec pre time = " + new Date());

        scheduledThreadPoolExecutor.scheduleAtFixedRate(task, 5L, 2L, TimeUnit.SECONDS);
    }


}
