package com.yipeng.dependency.inject.others;

import com.yipeng.dependency.inject.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

/**
 * {@link java.lang.reflect.AnnotatedElement} 接口示例
 * <p>
 * 实现了该接口的类代表是一个 <strong>可以被注解的</strong> 元素。
 * 比如：{@link Class}、{@link Field}、{@link Constructor}、{@link Method} 、{@link Parameter} 等
 * <p>
 * 使用该接口的中的方法用于获取被注解元素上的注解
 *
 * @author yipeng
 */
public class AnnotatedElementDemo {

    public static final String FIELD_USER = "user";
    // 获取到该属性上面的所有的注解信息
    @Autowired(required = false)
    @Qualifier("qualifier")
    @Lazy(value = false)
    private User user;

    public static void main(String[] args) throws NoSuchFieldException {
        Class<AnnotatedElementDemo> demoClass = AnnotatedElementDemo.class;
        // 获取到demoClass上面的所有的注解
        Annotation[] annotations = demoClass.getAnnotations();
        // 0
        System.out.println("demoClass annotations size: " + annotations.length);
        Arrays.stream(annotations).forEach(System.out::println);

        Field userField = demoClass.getDeclaredField(FIELD_USER);
        Annotation[] userFieldAnnotations = userField.getAnnotations();
        // 3
        System.out.println("userField annotations size: " + userFieldAnnotations.length);

        System.out.println("Autowired annotation exist in userField: " + userField.isAnnotationPresent(Autowired.class));
        System.out.println("userField get primary annotation: " + userField.getAnnotation(Primary.class));
        Lazy[] lazyAnnotationsByType = userField.getAnnotationsByType(Lazy.class);
        System.out.println("userField getAnnotationsByType lazyAnnotationsByType = " + lazyAnnotationsByType);


    }


}
