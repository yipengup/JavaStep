package com.yipengup.chapter7.concurrentlinkedqueue;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 测试并发链表队列相关
 *
 * @author yipengup
 * @date 2022/1/13
 */
public class ConcurrentLinkedQueueTest {

    public static void main(String[] args) {
        add();
    }

    /**
     * 添加元素
     */
    private static void add() {
        ConcurrentLinkedQueue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        concurrentLinkedQueue.add("1");
        concurrentLinkedQueue.add("2");
        concurrentLinkedQueue.forEach(System.out::println);
    }


}
