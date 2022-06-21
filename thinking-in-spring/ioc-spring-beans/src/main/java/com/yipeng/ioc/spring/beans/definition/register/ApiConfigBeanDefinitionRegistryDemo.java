package com.yipeng.ioc.spring.beans.definition.register;

import com.yipeng.ioc.spring.beans.domain.User;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * 注册 通过 Api 配置的 BeanDefinition
 * <p>
 * 注册方式有两种：
 * 1、命名式的 BeanDefinition （注册 BeanDefinition 的时候，手动赋予 BeanDefinition BeanName ）
 * {@link org.springframework.beans.factory.support.BeanDefinitionRegistry#registerBeanDefinition(String, BeanDefinition)}
 * <p>
 * 2、未命名式的 BeanDefinition （注册 BeanDefinition 时，不会赋予 BeanDefinition BeanName，依赖 Spring 自动会填充 BeanName）
 * {@link org.springframework.beans.factory.support.BeanDefinitionReaderUtils#registerWithGeneratedName(AbstractBeanDefinition, BeanDefinitionRegistry)}
 *
 * @author yipeng
 */
public class ApiConfigBeanDefinitionRegistryDemo {

    public static void main(String[] args) {
        // 利用 Api 构建 BeanDefinition
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class)
                .addPropertyValue("id", 1)
                .addPropertyValue("name", "yipeng");

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("namedUserBean", beanDefinitionBuilder.getBeanDefinition());

        // 本质上就是和 DefaultBeanNameGenerator 生成的名称一样，然后在调用 beanFactory 的 registerBeanDefinition 方法注册 BeanDefinition
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinitionBuilder.getBeanDefinition(), beanFactory);

        // namedUserBean、com.yipeng.ioc.spring.beans.domain.User#0
        System.out.println("beanFactory = " + beanFactory);
    }
}
