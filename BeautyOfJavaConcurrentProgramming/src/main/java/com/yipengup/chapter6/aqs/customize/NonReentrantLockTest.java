package com.yipengup.chapter6.aqs.customize;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * NonReentrantLock测试工具
 * <p>
 * --
 * 生产者消费者模型
 *
 * @author yipengup
 * @date 2022/1/4
 */
public class NonReentrantLockTest {

    private final static Lock LOCK = new NonReentrantLock();

    // 资源为空的条件队列
    private final static Condition EMPTY = LOCK.newCondition();
    // 资源满的条件队列
    private final static Condition FULL = LOCK.newCondition();

    // 资源队列
    private final static Queue<Integer> QUEUE = new ConcurrentLinkedDeque<>();

    private final static Integer MAX_QUEUE_SIZE = 10;

    public static void main(String[] args) throws InterruptedException {

        Thread producer = new Thread(() -> {
            final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
            while (true) {
                LOCK.lock();
                try {
                    // 使用while循环避免虚假唤醒
                    while (QUEUE.size() == MAX_QUEUE_SIZE) {
                        FULL.await();
                    }
                    // 生产资源
                    System.out.println("queue size: " + QUEUE.size());
                    QUEUE.offer(threadLocalRandom.nextInt());
                    // 唤醒消费者线程进行消费资源
                    EMPTY.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    LOCK.unlock();
                }
            }
        });
        producer.setName("producer");
        producer.start();

        Thread consumer = new Thread(() -> {
            while (true) {
                LOCK.lock();
                try {
                    // 使用while循环避免虚假唤醒
                    while (QUEUE.size() == 0) {
                        EMPTY.await();
                    }
                    // 消费资源
                    System.out.println("queue size: " + QUEUE.size());
                    Integer value = QUEUE.poll();
                    System.out.println("value = " + value);
                    // 唤醒生产者进行生产数据
                    FULL.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    LOCK.unlock();
                }
            }
        });
        consumer.setName("consumer");
        consumer.start();

        producer.join();
        consumer.join();
    }


}
