package com.yipeng.understandingjvm.chapter3.memoryallocation;

/**
 * 本例演示： 新生区对象经过多少次GC后进入老年代（NaxTenuringThreshold）
 * <p>
 * -Xms20M
 * -Xmx20M
 * -Xmn10M
 * -XX:+UseSerialGC
 * -XX:+PrintGCDetails
 * -XX:+PrintGCDateStamps
 * -XX:SurvivorRatio=8
 * -XX:PretenureSizeThreshold=6M
 * -XX:MaxTenuringThreshold=1
 *
 * @author yipengup
 * @date 2022/1/9
 */
public class TestMaxTenuringThreshold {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        // allocation1 为256kb， 此时survivor区域容纳的下
        byte[] allocation1 = new byte[_1MB / 4];

        // Eden区为8M
        byte[] allocation2 = new byte[4 * _1MB];
        // 此时触发第一次MinorGC，allocation2 进入老年代，allocation1 进入survivor区， allocation1的GCAge = 1达到阈值
        byte[] allocation3 = new byte[4 * _1MB];

        allocation3 = null;

        // 触发第二次 minorGC， 此时allocation1进入老年代。 allocation3被回收， allocation4进= 进入Eden区
        byte[] allocation4 = new byte[4 * _1MB];
    }

}
