package com.yipeng.dependency.inject.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author yipeng
 */
@Getter
@Setter
@ToString
public class User {

    private Long id;

    /**
     * note：这里想要模拟自动注入的话，不能使用普通的类型，普通的类型不会自动注入
     * {@link org.springframework.beans.BeanUtils#isSimpleProperty(Class)}
     */
    private Name name;
}
