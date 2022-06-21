package com.yipeng.ioc.spring.beans.name.generator;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * {@link org.springframework.beans.factory.support.BeanNameGenerator}
 * 当没有指明 Bean 的 identifier 时，Spring 会自动为 Bean 生成相对于 BeanFactory 唯一的 BeanName
 * <p>
 * 默认会使用  {@link org.springframework.beans.factory.support.DefaultBeanNameGenerator}
 * 采用 {@link org.springframework.stereotype.Component} 以及其派生注解的话就会使用 {@link org.springframework.context.annotation.AnnotationBeanNameGenerator}
 * <p>
 * note: 如果使用 AnnotationBeanNameGenerator 的话，对于注解对应的 Bean，如果注解没有添加 value 相关属性设置 BeanName,
 * 那么多个不同 packageName 但是 className 会生成相同的 BeanName（会直接获取到 ClassName 然后首字母小写）
 * 对于 BeanFactory 来说 BeanName 需要具备唯一性，此时根据不同的 BeanFactory 实现，有不同的处理逻辑。
 * {@link DefaultListableBeanFactory#allowBeanDefinitionOverriding} 为 true 的话，表示允许被覆盖，后面的 Bean 会覆盖前面的 Bean，否则抛出异常
 *
 * @author yipeng
 */
public class DefaultBeanNameGeneratorDemo {

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
