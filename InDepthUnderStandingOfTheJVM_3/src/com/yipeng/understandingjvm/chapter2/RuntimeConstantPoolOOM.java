package com.yipeng.understandingjvm.chapter2;

import java.util.HashSet;
import java.util.Set;

/**
 * 运行时常量池内存溢出
 *      - 运行时常量池是方法区的一部分，直接设置永久代的大小即可。
 *
 *
 * -XX：PermSize=6M
 * -XX:MaxPermSize=6M
 *
 * JDK 7+ 版本不会出现OOM异常，因为JDK7+将原本存放在永久代的字符串常量池被移至Java堆之中。
 *
 *
 * @author yipengup
 * @date 2022/1/6
 */
public class RuntimeConstantPoolOOM {

    public static void main(String[] args) {
        // 这里保存每一个字符串，避免永久代被FullGC回收
        Set<String>  stringSet = new HashSet<>();
        long i = 0;
        while (true) {
            stringSet.add(String.valueOf(i++).intern());
        }
    }



}
