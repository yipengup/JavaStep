package com.yipengup.pojo;

import lombok.ToString;

/**
 * 封装时间
 *
 * @author yipengup
 * @date 2021/12/30
 */
@ToString
public class UnixTime {

    private final long value;

    public UnixTime() {
        this(System.currentTimeMillis() / 1000L);
    }

    public UnixTime(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

}
