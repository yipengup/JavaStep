package com.yipeng.dependency.inject.autowiring;

import com.yipeng.dependency.inject.domain.AutowireConstructor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 构造器注入示例
 * <p>
 * 对于构造器的参数自动注入的顺序：先按照属性的属性名查找对应的 Bean -> (未找到) -> 再按照属性对应的属性状态查找对应的 Bean
 *
 * @author yipeng
 */
public class AutowiringByConstructDemo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/META-INF/autowiring-byconstruct-context.xml");
        AutowireConstructor construct = context.getBean("autowireConstruct", AutowireConstructor.class);
        System.out.println("construct = " + construct);
    }
}
