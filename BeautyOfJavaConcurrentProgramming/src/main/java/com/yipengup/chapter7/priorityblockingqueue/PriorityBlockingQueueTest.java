package com.yipengup.chapter7.priorityblockingqueue;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * 测试优先级队列相关案例
 *
 * @author yipengup
 * @date 2022/1/18
 */
public class PriorityBlockingQueueTest {

    public static void main(String[] args) {
        add();
    }

    public static void add() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>(2);
        queue.add("s");
        queue.add("a");
        queue.add("b");
        queue.forEach(System.out::println);
    }

}
