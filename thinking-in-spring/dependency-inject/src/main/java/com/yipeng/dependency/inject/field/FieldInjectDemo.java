package com.yipeng.dependency.inject.field;

import com.yipeng.common.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 字段注入示例
 * <p>
 * 字段注入只提供手动通过注解的方式注入，可用的注解有：
 * {@link org.springframework.beans.factory.annotation.Autowired}
 * {@link javax.annotation.Resource}
 * {@link javax.inject.Inject} note：需要引入 JSR-303
 * <p>
 * note:
 * XML 装配的 Bean 最后是 {@link GenericBeanDefinition}，默认不会设置 {@link GenericBeanDefinition#factoryBeanName}
 * 配置类 Annotation 装配的 Bean 最后是 {@link ConfigurationClassBeanDefinitionReader.ConfigurationClassBeanDefinition}，
 * 默认会设置 {@link ConfigurationClassBeanDefinitionReader.ConfigurationClassBeanDefinition#factoryBeanName} 属性为当前的装配类
 * <p>
 * {@link Autowired} Spring 自动注入中， 不管是属性注入、构造器注入、方法注入，注入的流程如下：
 * 1、{@link DefaultListableBeanFactory#findAutowireCandidates(String, Class, DependencyDescriptor)}
 * 根据<strong>注入的类型</strong>获取到 BeanFactory 中所有的满足的候选 Beans
 * 然后根据 {@link  DefaultListableBeanFactory#isAutowireCandidate(String, DependencyDescriptor)} 过滤掉不符合的类型（基本上都符合）
 * 注意：如果自动注入的属性和配置 Bean 的配置类在同一个，那么就需要单独考虑特殊的场景。
 * 2、根据候选 Beans 挑出自动注入 Bean
 * a、候选 Beans 只有一个，将其自动注入
 * b、候选 Beans 有多个，根据 {@link  DefaultListableBeanFactory#determineAutowireCandidate(Map, DependencyDescriptor)} 挑选出优先级最高的一个
 * 判断是否有 {@link  org.springframework.context.annotation.Primary} 注解
 * 判断 {@link DefaultListableBeanFactory#matchesBeanName(String, String)} 注入的属性或者参数名称是否与候选的 Bean 一致
 * 以上都不满足的话抛出异常 {@link org.springframework.beans.factory.NoUniqueBeanDefinitionException}
 * <p>
 * <p>
 * {@link Resource} 注入的字段，通过 {@link Resource#name()} 指定在 Bean 容器中查找注入的名称， name 默认为属性名
 * <p>
 * 字段注入会忽略掉 static 修饰的类变量
 *
 * @author yipeng
 */
public class FieldInjectDemo {
    @Autowired
    private User user;

    // @resource 可指定在容器中查找 Bean 的 id，若是没有指定 name，默认就是属性名称
    // @Resource 只能通过名称进行依赖查找后注入
    // @Resource(name = "user")
    // private User user1;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 读取XML文件中的 Bean，注入到当前的容器中
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(applicationContext);
        reader.loadBeanDefinitions("classpath:/META-INF/injector-context.xml");

        applicationContext.register(FieldInjectorConfiguration.class, FieldInjectDemo.class);
        applicationContext.refresh();

        Map<String, User> userMap = applicationContext.getBeansOfType(User.class);
        System.out.println("userMap = " + userMap);

        FieldInjectDemo demo = applicationContext.getBean(FieldInjectDemo.class);
        User userField = demo.user;
        System.out.println("userField = " + userField);

        // User resourceUser = demo.user1;
        // System.out.println("resourceUser = " + resourceUser);

        applicationContext.close();
    }
}
