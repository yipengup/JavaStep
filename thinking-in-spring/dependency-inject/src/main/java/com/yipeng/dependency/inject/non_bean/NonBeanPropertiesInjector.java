package com.yipeng.dependency.inject.non_bean;

import com.yipeng.dependency.inject.domain.NonBeanPropertiesHolder;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 非常规 Bean 的属性的注入
 * 1、原生类型（Primitive）：boolean 、byte、char、short、int、float、long、double
 * 2、标量类型（Scalar）：Number、Character、Boolean、Enum、Locale、Charset、Currency、Properties、UUID
 * 3、常规类型（General）：Object、String、TimeZone、Calendar、Optional等
 * 4、Spring类型：Resource、InputSource、Formatter等
 * <p>
 * 非常规 Bean 的属性注入在 XML 配置注入的时候，一般都会使用到属性转换（根据字符串转换到指定的类型），
 * {@link java.beans.PropertyEditor}
 *
 * @author yipeng
 */
public class NonBeanPropertiesInjector {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/META-INF/non-bean-property-injector-context.xml");
        NonBeanPropertiesHolder holder = applicationContext.getBean(NonBeanPropertiesHolder.class);
        // holder = NonBeanPropertiesHolder(id=1, name=non-bean-properties-holder, city=BEIJING, configLocation=class path resource [META-INF/non-bean-property-injector-context.xml])
        System.out.println("holder = " + holder);
        applicationContext.close();
    }
}
