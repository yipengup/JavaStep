package com.yipengup.chapter6.aqs.customize;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 实现非可重入锁
 *
 * @author yipengup
 * @date 2022/1/4
 */
public class NonReentrantLock implements Lock, Serializable {

    private final Sync sync;

    public NonReentrantLock() {
        this.sync = new Sync();
    }

    /**
     * 主要通过 Sync管 理抽象同步队列
     */
    static class Sync extends AbstractQueuedSynchronizer {

        @Override
        protected boolean tryAcquire(int arg) {
            // 表示当前锁还没有被使用
            if (compareAndSetState(0, arg)) {
                // 设置被当前线程独占
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            // 释放锁
            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            // 重置 state 为0，清空独占线程
            setState(0);
            setExclusiveOwnerThread(null);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            // 判断当前同步锁是否被占用
            return getState() == 1;
        }

        final ConditionObject newCondition() {
            return new ConditionObject();
        }

    }

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
