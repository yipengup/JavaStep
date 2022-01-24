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
        printPoolInfoForThreadOverflow();
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


}
