package com.yipeng.ioc.spring.beans.definition;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * {@link org.springframework.beans.factory.support.BeanNameGenerator}
 * 当没有指明 Bean 的 identifier 时，Spring 会自动为 Bean 生成相对于 BeanFactory 唯一的 BeanName
 * <p>
 * 默认会使用  {@link org.springframework.beans.factory.support.DefaultBeanNameGenerator}
 * 采用注解的话就会使用 {@link org.springframework.context.annotation.AnnotationBeanNameGenerator}
 *
 * @author yipeng
 */
public class BeanNameGeneratorDemo {

    public static void main(String[] args) {

        // 采用 BeanFactory 去注册 Bean
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 定义解释 XML 资源文件的类 XmlBeanDefinitionReader 用于构建 Bean 的元信息
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        String location = "classpath:/META-INF/bean-name-generator.xml";
        int loadBeanDefinitionsCount = reader.loadBeanDefinitions(location);
        System.out.println("XML 加载的 BeanDefinition 个数 loadBeanDefinitionsCount = " + loadBeanDefinitionsCount);

        // 这里会打印 BeanFactory 管理的 Bean 信息，其中两个 User Bean 的名称为【com.yipeng.ioc.spring.beans.domain.User#0,com.yipeng.ioc.spring.beans.domain.User#1】
        // 这里就是 Spring 调用 DefaultBeanNameGenerator#generateBeanName 去生成的默认名称（User#0、User#1）
        System.out.println("beanFactory = " + beanFactory);
    }

}
