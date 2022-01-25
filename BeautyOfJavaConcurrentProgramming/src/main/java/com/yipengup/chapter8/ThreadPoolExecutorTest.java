package com.yipengup.chapter8;

import java.util.concurrent.*;

/**
 * 线程池相关案例
 *
 * @author yipengup
 * @date 2022/1/20
 */
public class ThreadPoolExecutorTest {


    public static void main(String[] args) throws InterruptedException {
        // printPoolInfoForThreadOverflow();
        queueOrMaximumPoolPriority();
    }

    /**
     * 创建线程池
     *
     * @return ExecutorService
     */
    private static ExecutorService threadPoolExecutorCreator() {

        // 获取到JVM支持的CPU个数
        // 核心线程个数一般设置为与支持的CPU个数有关，最大限度的利用CPU
        // keepAlive 一般设置为任务平均的执行时间 * 2
        int processors = Runtime.getRuntime().availableProcessors();

        // 使用有界链表队列，队列的大小设置与业务量有关
        LinkedBlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue<>(1000);
        ThreadFactory threadFactory = NamedThreadFactory.create("模拟创建线程池");
        CustomRejectedExecutionHandler customRejectedExecutionHandler = new CustomRejectedExecutionHandler();

        return new ThreadPoolExecutor(processors, processors * 2, 60, TimeUnit.SECONDS,
                linkedBlockingQueue, threadFactory, customRejectedExecutionHandler);
    }

    private static void printPoolInfoForThreadOverflow() throws InterruptedException {
        Runnable runnable = () -> {
            Thread thread = Thread.currentThread();
            System.out.println(thread.getName() + " running");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        };

        CustomRejectedExecutionHandler handler = new CustomRejectedExecutionHandler();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(5),
                NamedThreadFactory.create("printPoolInfoForThreadOverflow"), handler);

        // 创建任务, 查看任务溢出后线程池的情况
        for (int i = 0; i < 20; i++) {
            Thread.sleep(500);
            executor.submit(runnable);
        }

        // 打印相关信息
        Thread.sleep(20000);
        handler.rejectedExecution(null, executor);
    }

    /**
     * 验证任务是进入队列还是先创建线程到最大线程个数
     * <p>
     * 结论： 先创建核心线程，在将多余的任务放入到队列中，队列满后在创建线程到最大线程数
     */
    private static void queueOrMaximumPoolPriority() {
        Runnable task = () -> {
            System.out.println(Thread.currentThread().getName() + "running");
            for (; ; ) {

            }
        };

        // 核心线程 = 1， 最大线程 = 2， 队列 = 5
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(5), NamedThreadFactory.create("queueOrMaximumPoolPriority"), new ThreadPoolExecutor.DiscardPolicy());

        for (int i = 0; i < 2; i++) {
            System.out.println("=====================");
            executor.submit(task);
            // 返回正在主动执行任务的线程的大概数量
            System.out.println("active thread = " + executor.getActiveCount());
            // 返回队列的大小
            System.out.println("queue size = " + executor.getQueue().size());
            System.out.println("task Count = " + executor.getTaskCount());
            System.out.println("task Complement = " + executor.getCompletedTaskCount());
            System.out.println("=====================");
        }

    }

}
