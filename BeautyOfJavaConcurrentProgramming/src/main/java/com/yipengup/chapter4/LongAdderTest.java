package com.yipengup.chapter4;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author yipengup
 * @date 2021/12/29
 */
public class LongAdderTest {

    public static final LongAdder LONG_ADDER = new LongAdder();

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                LONG_ADDER.add(2);
            }).start();
        }

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        // 200
        System.out.println(LONG_ADDER.longValue());
    }
}
