package com.yipengup.chapter9;

import com.yipengup.chapter8.NamedThreadFactory;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 按照一定计划执行任务的线程池相关案例
 *
 * @author yipengup
 * @date 2022/1/25
 */
public class ScheduledThreadPoolExecutorTest {

    public static void main(String[] args) {

    }

    private static void executeTask() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1,
                NamedThreadFactory.create("executeTask"), new ThreadPoolExecutor.DiscardPolicy());

    }

}
