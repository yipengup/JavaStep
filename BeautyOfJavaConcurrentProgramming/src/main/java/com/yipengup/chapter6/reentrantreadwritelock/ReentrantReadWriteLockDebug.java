package com.yipengup.chapter6.reentrantreadwritelock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 演示 ReentrantReadWriteLock 情况
 *
 * @author yipengup
 * @date 2022/1/12
 */
public class ReentrantReadWriteLockDebug {

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) throws InterruptedException {
        // test1();
        twiceAcquireReadLock();
        // threeThreadAcquireReadLock();
    }

    /**
     * 演示线程获取到读锁之后是否还能获取到写锁
     */
    private static void test1() {
        ReentrantReadWriteLockDebug reentrantReadWriteLockDebug = new ReentrantReadWriteLockDebug();
        // 当前线程获取到读锁
        reentrantReadWriteLockDebug.lock.readLock().lock();
        try {

            System.out.println("require read lock");
            // 读写被获取之后，即使是当前线程也获取不到写锁
            reentrantReadWriteLockDebug.lock.writeLock().lock();
            try {
                System.out.println("require write lock");
            }finally {
                reentrantReadWriteLockDebug.lock.writeLock().unlock();
            }
        }finally {
            reentrantReadWriteLockDebug.lock.readLock().unlock();
        }
    }

    /**
     * 同一个线程获取两次读锁
     */
    private static void twiceAcquireReadLock() {
        ReentrantReadWriteLockDebug reentrantReadWriteLockDebug = new ReentrantReadWriteLockDebug();
        // 当前线程获取到读锁
        reentrantReadWriteLockDebug.lock.readLock().lock();
        try {

            System.out.println("require read lock");
            // 读写被获取之后，即使是当前线程也获取不到写锁
            reentrantReadWriteLockDebug.lock.readLock().lock();
            try {
                System.out.println("require read lock");
            }finally {
                reentrantReadWriteLockDebug.lock.readLock().unlock();
            }
        }finally {
            reentrantReadWriteLockDebug.lock.readLock().unlock();
        }
    }

    /**
     * 三个线程获取到读锁
     *
     * 使用 ThreadLocalHoldCounter 记录各个线程获取共享锁的次数
     */
    private static void threeThreadAcquireReadLock() {
        ReentrantReadWriteLockDebug reentrantReadWriteLockDebug = new ReentrantReadWriteLockDebug();
        Runnable runnable = () -> {
            // 当前线程获取到读锁
            reentrantReadWriteLockDebug.lock.readLock().lock();
            try {
                for (; ; ) {

                }
            } finally {
                reentrantReadWriteLockDebug.lock.readLock().unlock();
            }
        };

        new Thread(runnable, "thread1").start();
        new Thread(runnable, "thread2").start();
        new Thread(runnable, "thread3").start();

    }




}
