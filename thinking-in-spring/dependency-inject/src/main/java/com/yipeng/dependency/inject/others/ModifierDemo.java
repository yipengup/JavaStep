package com.yipeng.dependency.inject.others;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;

/**
 * {@link java.lang.reflect.Modifier} 修饰符示例
 * <p>
 * Modifier 表示的是类、方法、属性等的修饰符，比如 public、private、static、final、synchronized 等
 * 某个元素的修饰符集合被表示为整数，而修饰符存在在整数的二进制中的不同位上。
 * 可通过该元素对应的反射类去获取到修饰符集合整数。比如
 * {@link Constructor#getModifiers()}、{@link Class#getModifiers()} ..
 * <p>
 * {@link java.lang.reflect.Modifier} 提供了大量的静态方法去解析修饰符整数，去判断元素是否拥有某个修饰符
 * {@link java.lang.reflect.Modifier#isPublic(int)}、{@link java.lang.reflect.Modifier#isStatic(int)} ...
 *
 * @author yipeng
 */
public final class ModifierDemo {

    private final int temp = 1;

    public static void main(String[] args) {
        int ModifierDemoClassModifiers = ModifierDemo.class.getModifiers();
        Field field = ReflectionUtils.findField(ModifierDemo.class, "temp");
        Objects.requireNonNull(field);
        int fieldModifiers = field.getModifiers();

        System.out.println("Modifier.isPublic(ModifierDemoClassModifiers) = " + Modifier.isPublic(ModifierDemoClassModifiers));
        System.out.println("Modifier.isFinal(ModifierDemoClassModifiers) = " + Modifier.isFinal(ModifierDemoClassModifiers));
        System.out.println("Modifier.isStatic(ModifierDemoClassModifiers) = " + Modifier.isStatic(ModifierDemoClassModifiers));


        System.out.println("==========");

        System.out.println("Modifier.isPublic(fieldModifiers) = " + Modifier.isPublic(fieldModifiers));
        System.out.println("Modifier.isPrivate(fieldModifiers) = " + Modifier.isPrivate(fieldModifiers));
        System.out.println("Modifier.isFinal(fieldModifiers) = " + Modifier.isFinal(fieldModifiers));
        System.out.println("Modifier.isStatic(fieldModifiers) = " + Modifier.isStatic(fieldModifiers));
    }

}
