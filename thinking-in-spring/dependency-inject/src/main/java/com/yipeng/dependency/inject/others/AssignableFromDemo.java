package com.yipeng.dependency.inject.others;

import com.yipeng.common.domain.User;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link Class#isAssignableFrom(Class)} 与 {@link instanceof} 的区别
 * <p>
 * 1、instanceof 用来判断一个对象是否是另一个类或者接口的实例
 * 2、{@link Class#isAssignableFrom(Class)} 用来判断一个 Class 是否与另一个 CLass相同，或者当前的 Class 是否是指定 Class 的超类或者接口
 *
 * @author yipeng
 */
public class AssignableFromDemo {

    public static void main(String[] args) {
        // 用来判断一个 Class 是否与另一个 CLass相同，或者当前的 Class 是否是指定 Class 的超类或者接口
        // 前面是同级类或者超类
        System.out.println(Object.class.isAssignableFrom(User.class));
        System.out.println(Map.class.isAssignableFrom(HashMap.class));
        // 判断一个对象是否是另一个类或者接口的实例
        // 前面是同级对象或者是子对象
        System.out.println(User.createUser() instanceof Object);
    }
}
