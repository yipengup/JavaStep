package com.yipengup.chapter6.reentrantlock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 本例演示： 使用 ReentrantLock 实现线程安全的ArrayList
 * @author yipengup
 * @date 2022/1/10
 */
public class ReentrantLockList<T> {

    private final ReentrantLock reentrantLock = new ReentrantLock();
    private final List<T> objectList = new ArrayList<>();

    public boolean add(T t) {
        reentrantLock.lock();
        try {
            return objectList.add(t);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
        return false;
    }

    public boolean remove(T t) {
        reentrantLock.lock();
        try {
            return objectList.remove(t);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
        return false;
    }

    public T get(int index) {
        reentrantLock.lock();

        try {
            return objectList.get(index);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
        return null;
    }

}
