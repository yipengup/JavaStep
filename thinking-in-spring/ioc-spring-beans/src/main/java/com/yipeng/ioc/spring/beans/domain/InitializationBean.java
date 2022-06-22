package com.yipeng.ioc.spring.beans.domain;

import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;

/**
 * @author yipeng
 */
public class InitializationBean implements InitializingBean {

    @PostConstruct
    public void initByPostConstruct() {
        System.out.println("InitializationBean Initialized By PostConstruct");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializationBean Initialized By InitializingBean");
    }

    public void initByInitMethod() {
        System.out.println("InitializationBean Initialized By initMethod");
    }
}
