package com.yipeng.ioc.java.beans;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;

/**
 * 修改JavaBean的属性信息
 *
 * 一般前端或者GUI或者配置文件中传递到后端的是String，而Bean的property的类型是非String，如Integer。
 * 此时就需要将 String => Integer
 *
 *
 * @author yipeng
 */
public class PropertyEditDemo {

    public static void main(String[] args) throws IntrospectionException {

        // 获取到Bean的描述性信息
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class, Object.class);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        Arrays.stream(propertyDescriptors).forEach(propertyDescriptor -> {
            // 设置age属性的 propertyEdit
            Class<?> propertyType = propertyDescriptor.getPropertyType();
            if (Integer.class == propertyType) {
                // PropertyEditor 的功能就是将外部设置的值转化为 JVM 内部的对应的类型
                // 将整数类型的属性编辑器设置为 String2IntegerPropertyEditor
                propertyDescriptor.setPropertyEditorClass(String2IntegerPropertyEditor.class);
            }

        });
    }


}
