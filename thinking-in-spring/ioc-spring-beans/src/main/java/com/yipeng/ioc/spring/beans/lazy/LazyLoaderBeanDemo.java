package com.yipeng.ioc.spring.beans.lazy;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

/**
 * 延迟加载 Bean 示例
 * 延迟加载 Bean 的表现在于：使用 Bean 被依赖查找或其他使用的时候才会被实例化+初始化
 * <p>
 * 延迟的方式有两种：
 * 1、{@link org.springframework.context.annotation.Lazy}
 * 2、XML 配置 Bean 指定 lazy-init 属性
 *
 * @author yipeng
 */
public class LazyLoaderBeanDemo {

    @Bean
    @Lazy
    public LazyBean lazyBean() {
        return new LazyBean();
    }

    @Bean
    public NonLazyBean nonLazyBean() {
        return new NonLazyBean();
    }

    public static void main(String[] args) {

        /*
        refresh pre
        NonLazyBean Construct
        NonLazyBean Initialization
        refresh post
        LazyBean Construct
        LazyBean Initialization
         */

        // 从输出可以看出， 标注延迟加载之后，只有在 Bean 被使用的时候才会被实例化+初始化
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(LazyLoaderBeanDemo.class);
        System.out.println("refresh pre");
        applicationContext.refresh();
        System.out.println("refresh post");
        applicationContext.getBean(LazyBean.class);
        applicationContext.close();
    }
}
