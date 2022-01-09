package com.yipengup.chapter6.locksupport;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * 互斥先进先出队列锁
 *
 * @author yipengup
 * @date 2021/12/30
 */
public class FIFOMutexQueueLock {
    // 当前锁是否被占用
    private final AtomicBoolean blocked = new AtomicBoolean(false);

    // 等待队列
    private final Queue<Thread> waiters = new ConcurrentLinkedDeque<>();

    /**
     * 获取锁
     */
    public void lock() {
        // 首先，将获取锁的线程加入到等待队列
        Thread currentThread = Thread.currentThread();
        waiters.offer(currentThread);
        boolean interrupted = false;

        // 判断等待队列第一个元素是否是当前线程；
        // 当前锁是否已经在工作
        while (waiters.peek() != currentThread || !blocked.compareAndSet(false, true)) {
            // 将当前线程挂起
            LockSupport.park(this);
            // 如果当前线程是因为中断结束阻断，重置中断状态
            if (Thread.interrupted()) {
                // 记录中断状态
                interrupted = true;
            }
        }

        // 线程获取到锁,从等待队列中移除
        waiters.remove();
        if (interrupted) {
            // 回复当前线程的中断状态
            currentThread.interrupt();
        }

    }

    /**
     * 释放锁
     */
    public void unlock() {
        // 重置Block状态，并且唤醒等待队列中的head线程
        blocked.set(false);
        LockSupport.unpark(waiters.peek());
    }

}
