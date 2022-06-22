package com.yipeng.ioc.spring.beans.initialization;

import com.yipeng.ioc.spring.beans.domain.InitializationBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 指定 Bean 初始化方法示例
 * <p>
 * 指定 Bean 的初始化方法方式有三种：
 * 1、利用 {@link javax.annotation.PostConstruct}
 * 2、Bean 实现 {@link InitializingBean#afterPropertiesSet()}
 * 3、指定 Bean 的 InitMethod（XML配置、{@link Bean#initMethod()、{@link org.springframework.beans.factory.support.AbstractBeanDefinition#setInitMethodName(String)}}）
 * <p>
 * 以上三种方式的优先级由高到低
 *
 * @author yipeng
 */
public class BeanInitializationDemo {

    // 指定初始化方法
    @Bean(initMethod = "initByInitMethod")
    public InitializationBean initializationBean() {
        return new InitializationBean();
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanInitializationDemo.class);
        applicationContext.refresh();
        applicationContext.close();
    }
}
