package com.yipeng.ioc.spring.beans.name.generator.domain_two;

import org.springframework.stereotype.Component;

/**
 * {@link Component} 未指定 BeanName 的话，会采用 {@link org.springframework.context.annotation.AnnotationBeanNameGenerator} 自动生成 BeanName
 *
 * @author yipeng
 */
@Component
public class Hello {
}
