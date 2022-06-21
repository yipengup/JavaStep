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

    /**
     * 静态工厂方法用于实例化User
     * 可用于替换构造器方法
     *
     * @return User
     */
    public static User createUser() {
        User user = new User();
        user.setId(1L);
        user.setName("yipeng");
        return user;
    }

}
