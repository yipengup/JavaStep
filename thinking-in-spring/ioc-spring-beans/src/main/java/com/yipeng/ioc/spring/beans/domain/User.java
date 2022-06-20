package com.yipeng.ioc.spring.beans.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 定义用户信息
 *
 * @author yipeng
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {

    private Long id;
    private String name;

}
