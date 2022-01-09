package com.yipeng.understandingjvm.chapter2;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 直接内存溢出
 * <p>
 * -XX:MaxDirectMemorySize=1024k
 * -Xmx20M
 *
 * @author yipengup
 * @date 2022/1/6
 */
public class DirectMemoryOOM {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) throws IllegalAccessException {
        Field field = Unsafe.class.getDeclaredFields()[0];
        field.setAccessible(true);

        Unsafe unsafe = (Unsafe) field.get(null);
        while (true) {
            // 分配1M的直接内存
            unsafe.allocateMemory(_1MB);
        }
    }
}
