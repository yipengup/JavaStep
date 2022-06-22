package com.yipeng.ioc.spring.beans.lazy;

import org.springframework.beans.factory.InitializingBean;

/**
 * 延迟加载 Bean
 * <p>
 * 用于判断延迟加载 Bean 的初始化还是延迟 Bean 的创建
 *
 * @author yipeng
 */
public class LazyBean implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("LazyBean Initialization");
    }

    public LazyBean() {
        System.out.println("LazyBean Construct");
    }
}
