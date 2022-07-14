package com.yipeng.dependency.inject.domain;

import com.yipeng.dependency.inject.enums.City;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.core.io.Resource;

/**
 * 非常规 Bean 属性类
 *
 * @author yipeng
 */
@Getter
@Setter
@ToString
public class NonBeanPropertiesHolder {

    // 常规类型
    private int id;
    private String name;

    // 标量类型之枚举类型
    private City city;

    // Spring 类型之 Resource
    private Resource configLocation;

}
