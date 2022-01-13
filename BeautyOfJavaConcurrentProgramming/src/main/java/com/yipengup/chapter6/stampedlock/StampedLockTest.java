package com.yipengup.chapter6.stampedlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.StampedLock;

/**
 * 测试 StampedLock 相关问题
 * @author yipengup
 * @date 2022/1/13
 */
public class StampedLockTest {

    private final StampedLock stampedLock = new StampedLock();

    public static void main(String[] args) {
        StampedLockTest stampedLockTest = new StampedLockTest();
        stampedLockTest.twiceAcquireReadLock();
    }

    /**
     * 模拟两次获取悲观读锁
     */
    private void twiceAcquireReadLock() {
        long readLockStamp = stampedLock.readLock();
        try {
            System.out.println("first acquire read lock");
            long secondReadLockStamp = stampedLock.readLock();
            try {
                System.out.println("second acquire read lock");
            } finally {
                stampedLock.unlockRead(secondReadLockStamp);
            }
        } finally {
            stampedLock.unlockRead(readLockStamp);
        }


    }

}
