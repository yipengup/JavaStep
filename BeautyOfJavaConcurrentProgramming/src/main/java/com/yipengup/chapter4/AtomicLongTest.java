package com.yipengup.chapter4;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 统计数组中0的个数
 *
 * @author yipengup
 * @date 2021/12/29
 */
public class AtomicLongTest {

    private static final AtomicLong ATOMIC_LONG = new AtomicLong();

    public static void main(String[] args) {
        int[] inta = {0, 1, 2, 3, 34, 450, 0, 0};
        int[] intB = {0, 0, 0, 1};

        new Thread(() -> {
            for (int i : inta) {
                if (i == 0) {
                    ATOMIC_LONG.incrementAndGet();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i : intB) {
                if (i == 0) {
                    ATOMIC_LONG.incrementAndGet();
                }
            }
        }).start();

        if (Thread.activeCount() >1) {
            Thread.yield();
        }

        System.out.println("0 size: " + ATOMIC_LONG.get());
    }


}
