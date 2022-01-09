package com.yipengup.chapter6.aqs;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 模拟基于AQS的条件队列
 *
 * @author yipengup
 * @date 2022/1/4
 */
public class AQSWaiterObjectTest {

    private static final ReentrantLock REENTRANT_LOCK = new ReentrantLock();

    // 创建一个条件队列（内部维护一个单项链表等待队列）
    private static final Condition CONDITION = REENTRANT_LOCK.newCondition();

    public static void main(String[] args) throws InterruptedException {

        Runnable runnable = () -> {
            try {
                String name = Thread.currentThread().getName();
                System.out.println(name + "get lock pre");
                REENTRANT_LOCK.lock();
                System.out.println(name + "await pre");
                CONDITION.await();
                System.out.println(name + "await end");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                REENTRANT_LOCK.unlock();
            }
        };

        Thread t1 = new Thread(runnable);
        t1.setName("t1");
        t1.start();

        Thread t2 = new Thread(runnable);
        t2.setName("t2");
        t2.start();

        Thread signalThread = new Thread(() -> {
            LockSupport.parkNanos(1000000);
            String name = Thread.currentThread().getName();
            System.out.println(name + "get lock pre");
            REENTRANT_LOCK.lock();
            System.out.println(name + "signal pre");
            try {
                CONDITION.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                REENTRANT_LOCK.unlock();
            }
        });

        signalThread.setName("signalThread");
        signalThread.start();

        t1.join();
        t2.join();
        signalThread.join();

        System.out.println("main end");

    }
}
