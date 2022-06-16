package com.yipeng.ioc.java.beans;

/**
 * 利用JavaBeans容器管理Person类
 *
 * 在Java Bean中 Getter / Setter 方法分别是 Readable（可读的方式） / Writable （可写的方法）
 *
 * @author yipeng
 */
public class Person {

    /**
     * 对于JavaBean来说， 属性就是 property
     */
    private String name;

    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
