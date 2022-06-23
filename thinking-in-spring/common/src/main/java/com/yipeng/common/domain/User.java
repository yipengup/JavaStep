package com.yipeng.common.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author yipeng
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    private static final AtomicLong ID_GENERATOR = new AtomicLong();
    public static final String DEFAULT_NAME = "yipeng";

    private Long id;
    private String name;

    public static User createUser() {
        return createUser(DEFAULT_NAME);
    }

    public static User createUser(String name) {
        User user = new User();
        user.setId(ID_GENERATOR.incrementAndGet());
        user.setName(name);
        return user;
    }
}
