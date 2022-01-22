package com.yipengup.chapter8;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义饱和超时策略
 *
 * @author yipengup
 * @date 2022/1/20
 */
public class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        // 先将相关的信息打印
        int activeCount = executor.getActiveCount();
        System.out.println("activeCount = " + activeCount);
        long completedTaskCount = executor.getCompletedTaskCount();
        System.out.println("completedTaskCount = " + completedTaskCount);
        long taskCount = executor.getTaskCount();
        System.out.println("taskCount = " + taskCount);
        // 获取到阻塞队列中的大小
        int size = executor.getQueue().size();
        System.out.println("size = " + size);
        int largestPoolSize = executor.getLargestPoolSize();
        System.out.println("largestPoolSize = " + largestPoolSize);

        // 直接抛弃当前任务

    }
}
