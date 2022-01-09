package com.yipeng.understandingjvm.chapter3.memoryallocation;

/**
 * 大多数对象在新生代 Eden中 分配
 * - Eden 没有足够的空间时，就会触发 MinorGC
 * <p>
 * -Xms20M
 * -Xmx20M
 * -Xmn10M
 * -XX:+UseParNewGC
 * -XX:SurvivorRatio=8
 * -XX:+PrintGCDetails
 * -XX:+PrintGCDateStamps
 * -XX:PretenureSizeThreshold=6291456
 *
 * @author yipengup
 * @date 2022/1/7
 */
public class EdenAllocationTest {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        byte[] allocation1, allocation2, allocation3, allocation4;
        // 依次创建3个 2MB的对象 和 1个4MB的对象
        // 注意使用 Serial或 ParNew ，不然4G的大对象会直接进入老年代
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        // 此时新生区容纳不下4G的对象，触发MinorGC， allocation1，allocation2，allocation3因为survivor区域容纳不下直接进入老年代
        allocation4 = new byte[4 * _1MB];
    }

}
