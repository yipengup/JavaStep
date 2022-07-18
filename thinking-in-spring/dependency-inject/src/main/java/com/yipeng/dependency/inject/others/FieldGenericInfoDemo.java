package com.yipeng.dependency.inject.others;

import com.yipeng.common.domain.User;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * 获取属性的泛型信息 {@link Field#getGenericType()}
 *
 * @author yipeng
 */
public class FieldGenericInfoDemo {

    private List<User> users;

    public static void main(String[] args) throws NoSuchFieldException {
        Class<FieldGenericInfoDemo> demoClass = FieldGenericInfoDemo.class;
        Field usersField = demoClass.getDeclaredField("users");
        // 提取泛型信息
        Type type = usersField.getGenericType();
        System.out.println("type = " + type);
        System.out.println("type.getClass().getName() = " + type.getClass().getName());
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            System.out.println("parameterizedType.rawType = " + parameterizedType.getRawType());
            System.out.println("parameterizedType.getTypeName() = " + parameterizedType.getTypeName());

            // 获取到泛型信息列表
            Type[] genericTypes = parameterizedType.getActualTypeArguments();
            Arrays.stream(genericTypes).forEach(genericType -> {
                // genericType.getTypeName() = com.yipeng.common.domain.User
                System.out.println("parameterizedType.getActualTypeArguments()，genericType.getTypeName() = " + genericType.getTypeName());
            });
        }
    }

}
