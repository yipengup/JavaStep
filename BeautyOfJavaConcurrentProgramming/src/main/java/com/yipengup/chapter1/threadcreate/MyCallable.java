package com.yipengup.chapter1.threadcreate;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建线程方式三：实现Callable接口
 * <p>
 * 优点：
 * 1、执行的任务可以有返回值
 * 2、执行的任务可以被多个线程重复使用
 *
 * @author yipengup
 * @date 2021/12/11
 */
public class MyCallable implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println("MyCallable.call");
        // 当前线程休眠5秒
        Thread.sleep(5000);
        System.out.println("结束休眠");
        return 1;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        MyCallable myCallable = new MyCallable();
        FutureTask<Integer> futureTask = new FutureTask<>(myCallable);

        Thread thread = new Thread(futureTask);
        thread.start();
        System.out.println("MyCallable.main");
        // 采用死循环的方式等待子线程执行完毕并返回值
        while (!futureTask.isDone()) {
            System.out.println("!futureTask.isDone()");
        }
        // get方法本身就会阻塞当前获取到结果
        Integer integer = futureTask.get();
        System.out.println("integer = " + integer);
    }
}
