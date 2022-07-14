package com.yipeng.dependency.inject.domain;

import com.yipeng.dependency.inject.enums.City;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 集合类型注入演示 Bean
 *
 * @author yipeng
 */
@Getter
@Setter
@ToString
public class CollectionHolder {

    private City[] workCity;
    private List<City> lifeCity;

}
