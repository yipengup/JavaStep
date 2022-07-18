package com.yipeng.dependency.inject.field;

import com.yipeng.common.domain.User;
import org.springframework.context.annotation.Bean;

/**
 * 属性注入配置类
 *
 * @author yipeng
 */
public class FieldInjectorConfiguration {

    // 设置 Bean 的 id 为 userByType
    @Bean
    public User userByType() {
        return User.createUser("userByType");
    }

    // 设置 Bean 的 id 为 user
    @Bean
    public User user() {
        return User.createUser("userByName");
    }

    // @Bean
    // @Primary
    // public User userPrimary() {
    //     return User.createUser("userByPrimary");
    // }


}
