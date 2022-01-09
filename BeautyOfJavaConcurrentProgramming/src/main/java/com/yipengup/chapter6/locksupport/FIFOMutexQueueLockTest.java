package com.yipengup.chapter6.locksupport;

/**
 * @author yipengup
 * @date 2021/12/30
 */
public class FIFOMutexQueueLockTest {

    // 获取到一个先进先出队列互斥锁
    private static final FIFOMutexQueueLock LOCK = new FIFOMutexQueueLock();


    public static void main(String[] args) throws InterruptedException {

        Thread subThread = new Thread(() -> {
            try {
                Thread thread = Thread.currentThread();
                String threadName = thread.getName();
                System.out.println(threadName + "获取锁之前");
                LOCK.lock();
                // 死循环不释放
                for (; ; ) {

                }
            } finally {
                LOCK.unlock();
            }

        });

        subThread.setName("subThread");
        subThread.start();

        Thread.sleep(1000);

        // 子线程获取到锁之后会阻塞，通过jstat查看相关堆栈信息
        try {
            LOCK.lock();
        } finally {
            LOCK.unlock();
        }

        System.out.println("main end");
    }
}
