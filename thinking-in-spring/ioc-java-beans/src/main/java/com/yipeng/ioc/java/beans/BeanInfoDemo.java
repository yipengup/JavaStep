package com.yipeng.ioc.java.beans;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;

/**
 * 打印JavaBeans中管理的JavaBean的相关信息
 *
 * @author yipeng
 */
public class BeanInfoDemo {

    public static void main(String[] args) throws IntrospectionException {

        // 获取到Person 的JavaInfo信息。
        // 这里为了防止父类Object影响，将Object作为第二个参数，表示只需要获取当前类的信息，不需要再往上找了
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class, Object.class);
        // 获取到所有的属性，包括该属性是否可读或者是可写的（Readable or Writable => Getter or Setter）
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        Arrays.stream(propertyDescriptors).forEach(System.out::println);

    }
}
