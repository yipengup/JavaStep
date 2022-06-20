package com.yipeng.ioc.spring.beans.definition;

import com.yipeng.ioc.spring.beans.domain.User;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * {@link org.springframework.beans.factory.config.BeanDefinition }
 * <p>
 * 通过硬编码的方式创建 BeanDefinition 设置 Bean 的元信息
 *
 * @author yipeng
 */
public class BeanDefinitionDemo {

    public static void main(String[] args) {
        // 1. 通过 BeanDefinitionBuilder 建造器方式去构建 BeanDefinition.
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        // 设置相关的属性信息
        beanDefinitionBuilder
                .addPropertyValue("id", 1)
                .addPropertyValue("name", "yipeng");
        // 获取到 BeanDefinition
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        System.out.println("BeanDefinitionBuilder beanDefinition = " + beanDefinition);

        // 2. 直接通过 BeanDefinition 或者其派生类定义 BeanDefinition （这里通过 GenericBeanDefinition 进行定义）
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        // 设置 class 信息
        genericBeanDefinition.setBeanClass(User.class);
        // 通过 MutablePropertyValue 设置相关的属性信息
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues
                .add("id", 1)
                .add("name", "yipeng");

        genericBeanDefinition.setPropertyValues(propertyValues);
        System.out.println("genericBeanDefinition = " + genericBeanDefinition);
    }
}
