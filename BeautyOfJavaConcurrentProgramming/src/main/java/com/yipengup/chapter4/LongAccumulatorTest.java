package com.yipengup.chapter4;

import java.util.concurrent.atomic.LongAccumulator;

/**
 * 自定义实现累加器
 * LongAdder 是 LongAccumulator 的特殊实例
 *
 * @author yipengup
 * @date 2021/12/29
 */
public class LongAccumulatorTest {

    public static void main(String[] args) {
        // 定义将左右两个值相乘的累加器
        LongAccumulator accumulator = new LongAccumulator((left, right) -> left * right, 1);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                accumulator.accumulate(2);
            }).start();
        }

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        // 1024
        System.out.println(accumulator.longValue());

    }
}
