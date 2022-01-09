package com.yipengup.chapter2;

/**
 * @author yipengup
 * @date 2021/12/22
 */
public class ThreadNoSafeCount {

    private Long value;

    public void inc() {
        value++;
    }

}
