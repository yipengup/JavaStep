package com.yipeng.ioc.spring.beans.lazy;

import org.springframework.beans.factory.InitializingBean;

/**
 * 非延迟加载 Bean
 *
 * @author yipeng
 */
public class NonLazyBean implements InitializingBean {


    public NonLazyBean() {
        System.out.println("NonLazyBean Construct");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("NonLazyBean Initialization");
    }
}
