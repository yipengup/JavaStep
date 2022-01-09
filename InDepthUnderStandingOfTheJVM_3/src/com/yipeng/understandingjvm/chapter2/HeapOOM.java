package com.yipeng.understandingjvm.chapter2;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现堆内存移除
 *
 * 当堆内存达到 -Xmx 时，无法在申请内存时就会抛出OOM异常
 *
 * -Xms20m
 * -Xmx20m
 * -XX:+HeapDumpOnOutOfMemoryError
 *
 * @author yipengup
 * @date 2022/1/5
 */
public class HeapOOM {

    static class StaticObject {

    }

    public static void main(String[] args) {
        List<StaticObject> list = new ArrayList<>();

        while (true) {
            // 根据可达性分析，引用保存在list中，所以不能被GC回收
            list.add(new StaticObject());
        }
    }

}
