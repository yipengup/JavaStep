package com.yipengup.chapter1.threadwaitandnotify;

import java.util.ArrayDeque;
import java.util.Random;

/**
 * 生产者消费者
 *
 * @author yipengup
 * @date 2021/12/14
 */
public class SupplierAndConsumer {

    public static final ArrayDeque<String> QUEUE = new ArrayDeque<>();
    // 队列最大元素个数
    public static final int MAX_QUEUE_SIZE = 10;
    private static final char[] CHARS = {'a', 'b', 'c', 'd'};

    public static void main(String[] args) {

        Thread consumer = new Thread(() -> {
            while (true) {
                synchronized (QUEUE) {
                    // 防止虚假唤醒，当队列为空时直接直接让当前线程无限期等待
                    while (QUEUE.isEmpty()) {
                        try {
                            QUEUE.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    // 此时队列中有元素可以消费
                    String poll = QUEUE.poll();
                    System.out.println(Thread.currentThread().getName() + "：取出元素：" + poll);
                    // 消费完之后通知生产者线程继续生产元素
                    QUEUE.notifyAll();
                }
            }
        });
        consumer.setName("consumer");
        consumer.start();

        Thread supplier = new Thread(() -> {
            while (true) {
                synchronized (QUEUE) {
                    // 防止虚假唤醒，当队列满时直接直接让当前线程无限期等待
                    while (QUEUE.size() == MAX_QUEUE_SIZE) {
                        try {
                            QUEUE.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    // 此时队列中可以添加元素
                    Random random = new Random();
                    int i = random.nextInt(4);
                    char aChar = CHARS[i];
                    System.out.println(Thread.currentThread().getName() + "：添加元素：" + aChar);
                    QUEUE.offer(String.valueOf(aChar));
                    System.out.println("当前队列元素的个数：" + QUEUE.size());
                    // 消费完之后通知生产者线程继续生产元素
                    QUEUE.notifyAll();
                }
            }
        });
        supplier.setName("supplier");
        supplier.start();
    }


}
