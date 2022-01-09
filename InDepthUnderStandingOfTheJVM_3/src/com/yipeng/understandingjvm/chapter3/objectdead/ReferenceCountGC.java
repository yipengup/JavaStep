package com.yipeng.understandingjvm.chapter3.objectdead;

/**
 * 引用计数算法
 * 对象相互循环引用问题
 * <p>
 * -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+UseSerialGC
 *
 * @author yipengup
 * @date 2022/1/6
 */
public class ReferenceCountGC {

    private ReferenceCountGC instance = null;

    private static final int _1MB = 1024 * 1024;

    // 这个成员属性的唯一意义就是占内存，以便能在GC日志中看清楚是否有回收过
    private byte[] bigSize = new byte[2 * _1MB];

    public static void main(String[] args) {
        ReferenceCountGC referenceCountGCA = new ReferenceCountGC();
        ReferenceCountGC referenceCountGCB = new ReferenceCountGC();

        // 使对象相互引用
        referenceCountGCA.instance = referenceCountGCB;
        referenceCountGCB.instance = referenceCountGCA;

        // 将其设置为null， 如果时引用计数算法就不会被回收
        referenceCountGCA = null;
        referenceCountGCB = null;

        // 查看两个对象是否被回收
        System.gc();
    }


}
