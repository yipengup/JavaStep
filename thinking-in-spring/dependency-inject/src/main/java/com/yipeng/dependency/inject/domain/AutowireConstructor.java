package com.yipeng.dependency.inject.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 构造器自动注入
 *
 * @author yipeng
 */
@Getter
@Setter
@ToString
public class AutowireConstructor {

    private Name name;

    // 构造器注入
    public AutowireConstructor(Name name) {
        this.name = name;
    }
}
