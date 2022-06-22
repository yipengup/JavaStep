package com.yipeng.ioc.spring.beans.destroy;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Bean 的销毁触发的方法
 * <p>
 * 方法有三种：
 * 1、{@link javax.annotation.PreDestroy}
 * 2、{@link DisposableBean#destroy()}
 * 3、{@link Bean#destroyMethod()} or XML Bean （destroy-method 属性） or {@link org.springframework.beans.factory.support.AbstractBeanDefinition#setDestroyMethodName(String)}
 * <p>
 * 执行的优先级从高到低
 *
 * @author yipeng
 */
public class DestroyBeanDemo {

    @Bean(destroyMethod = "destroyMethod")
    public DestroyBean destroyBean() {
        return new DestroyBean();
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(DestroyBeanDemo.class);
        applicationContext.refresh();
        // close 方法触发 Bean 的销毁，Bean 销毁之前就会触发定义的相关的销毁方法
        applicationContext.close();
    }
}
