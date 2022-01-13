package com.yipengup.chapter6.reentrantreadwritelock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 利用可重入读写锁实现线程安全的ArrayList
 *
 * @author yipengup
 * @date 2022/1/13
 */
public class ReentrantReadWriteLockList<T> {

    private final ArrayList<T> arrayList = new ArrayList<>();
    private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = reentrantReadWriteLock.readLock();
    private final Lock writeLock = reentrantReadWriteLock.writeLock();

    public boolean add(T t) {
        writeLock.lock();
        try {
            return arrayList.add(t);
        } finally {
            writeLock.unlock();
        }
    }

    public T delete(int index) {
        writeLock.lock();
        try {
           return arrayList.remove(index);
        } finally {
            writeLock.unlock();
        }
    }

    public T get(int index) {
        readLock.lock();
        try {
            return arrayList.get(index);
        } finally {
            readLock.unlock();
        }
    }


}
