package com.yipengup.chapter6.aqs;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 模拟当在有一个线程获取到reentrantLock锁之后，另外一个获取锁的流程
 *
 * @author yipengup
 * @date 2021/12/31
 */
public class AbstractQueuedSynchronizerTest {

    public static void main(String[] args) throws InterruptedException {
        Thread.currentThread().setName("main");
        ReentrantLock reentrantLock = new ReentrantLock();
        Thread thread = new Thread(() -> {
            try {
                reentrantLock.lock();
                for (; ; ) {

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        });

        thread.setName("subThread");
        thread.start();

        Thread.sleep(1000);

        System.out.println("main try acquire lock...");
        try {
            reentrantLock.lock();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }

        System.out.println("main thread end");

    }
}
