package com.yipeng.understandingjvm.chapter3.memoryallocation;

/**
 * 本例演示： 动态对象年龄判定
 * 当 Survivor区域相同年纪的对象大小大于或者等于survivor区域的一半，那么等于或者大于该年纪的对象直接进入老年代
 * <p>
 * -Xms20M
 * -Xmx20M
 * -Xmn10M
 * -XX:+UseSerialGC
 * -XX:+PrintGCDetails
 * -XX:MaxTenuringThreshold=15
 * -XX:PretenureSizeThreshold=6M
 *
 * @author yipengup
 * @date 2022/1/9
 */
public class DynamicObjectGCAgeCharge {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        byte[] allocation1, allocation2;
        allocation1 = new byte[_1MB / 4];
        allocation2 = new byte[_1MB / 4];

        byte[] allocation3 = new byte[4 * _1MB];
        // 第一次GC（allocation1， allocation2同时进入survivor区）
        byte[] allocation4 = new byte[4 * _1MB];
        allocation4 = null;
        // 第二次GC 检查Survivor区域的对象（allocation1， allocation2 总内存为512K = 1M的一半，直接进入老年代）
        byte[] allocation5 = new byte[4 * _1MB];
    }

}
