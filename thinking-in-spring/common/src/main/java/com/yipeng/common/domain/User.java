package com.yipeng.common.domain;

import lombok.ToString;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author yipeng
 */
@ToString
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

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


