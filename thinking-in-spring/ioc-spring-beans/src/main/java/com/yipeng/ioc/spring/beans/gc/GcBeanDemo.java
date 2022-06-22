package com.yipeng.ioc.spring.beans.gc;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * gc Bean 回收 Spring Bean
 *
 * @author yipeng
 */
public class GcBeanDemo {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(GcBean.class).getBeanDefinition();
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, applicationContext);

        applicationContext.refresh();
        applicationContext.close();

        // 关闭容器之后手动触发垃圾收集
        System.gc();
        // 因为 gc 非当前线程，需要等待一段时间触发 Bean finalize
        Thread.sleep(10000);
    }
}
