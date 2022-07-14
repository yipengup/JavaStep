package com.yipeng.dependency.inject.collection;

import com.yipeng.dependency.inject.domain.CollectionHolder;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 集合类型注入示例
 * 1、数组类型（Array）：数组类型的组件类包括：原生类型、标量类型、常规类型、Spring类型
 * 2、集合类型（Collection）：
 * a、Collection：List、Set（SortedSet、NavigableSet、EnumSet）
 * b、Map：Properties
 *
 * @author yipeng
 */
public class CollectionInjectorDemo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/META-INF/collection-injector-context.xml");
        CollectionHolder holder = context.getBean(CollectionHolder.class);
        // holder = CollectionHolder(workCity=[SHANGHAI, BEIJING], lifeCity=[BEIJING, WUHAN])
        System.out.println("holder = " + holder);
    }

}
