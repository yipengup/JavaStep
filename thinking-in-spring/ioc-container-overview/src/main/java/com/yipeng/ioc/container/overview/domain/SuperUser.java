package com.yipeng.ioc.container.overview.domain;

import com.yipeng.ioc.container.overview.annotaion.Super;

/**
 * 超级用户
 * 对于 Bean Factory 来说，按照类型查看的话，SuperUser 也属于 User 类型
 *
 * @author yipeng
 */
@Super
public class SuperUser extends User{

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SuperUser{" +
                "address='" + address + '\'' +
                "} " + super.toString();
    }
}
