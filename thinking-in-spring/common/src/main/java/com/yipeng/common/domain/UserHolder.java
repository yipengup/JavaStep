package com.yipeng.common.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author yipeng
 */
@ToString
@EqualsAndHashCode
public class UserHolder {

    private User user;

    public UserHolder() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
