package com.yipeng.dependency.inject.autowiring;

import com.yipeng.common.domain.User;
import com.yipeng.dependency.inject.autowiring.annotations.MyAutowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 自定义注解实现与 {@link Autowired} 注解功能
 * <p>
 * Spring 利用 {@link AutowiredAnnotationBeanPostProcessor} 获取到 Bean 中携带
 * {@link AutowiredAnnotationBeanPostProcessor#autowiredAnnotationTypes} 指定注解的属性进行自动注入。
 * 默认自动注入的注解有 {@link Autowired}、{@link Value}、{@link javax.inject.Inject}
 * <p>
 * 实现目的的方式有两种：
 * 1、Spring 在创建 Bean 对象的时候，会获取到所有的 BeanPostProcessor去处理 Bean 的创建，所以可以再定义一个 {@link AutowiredAnnotationBeanPostProcessor} 去处理自定义注解，不影响原有的能力。
 * 2、Spring 默认会利用 {@link AnnotationConfigUtils#registerAnnotationConfigProcessors} 去创建 id 为 {@link AnnotationConfigUtils#AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME} 的 BeanPostProcessor，
 * 此时可以自定义一个相同的 id 的 Bean 去替换掉原有的 Bean，然后利用 {@link AutowiredAnnotationBeanPostProcessor#setAutowiredAnnotationTypes(Set)} 去将所有的自动注入的注解添加到集合中。
 * <p>
 * ext:
 * 1、Spring 中利用 {@link CommonAnnotationBeanPostProcessor} 去处理 {@link javax.annotation.Resource} 注解
 * 2、Spring 中实例化 Bean 的时候，如果 Bean 的 {@link BeanDefinition#getFactoryBeanName()} 不为空，会先实例化工厂Bean
 *
 * @author yipeng
 */
public class CustomizedAutowiredAnnotationDemo {

    @Autowired
    private User user;

    // 自定义自动注入的注解
    @MyAutowired
    private User myUser;

    @Bean
    private User nonStaticUser() {
        return User.createUser("nonStaticUser");
    }

    // 在配置类中如果方法是静态方式，那么 BeanDefinition#factoryBeanName 为 null 而不是配置类的名称
    // 这会影响到自动注入发现候选 Bean 的逻辑，最后会先将其自动注入到 User 类型的属性中，而不是抛出 NoUniqueBeanDefinitionException
    @Bean
    private static User staticUser() {
        return User.createUser("staticUser");
    }

    // 注入自定义 AutowiredAnnotationBeanPostProcessor
    // 在配置类中如果方法是静态方式，那么 BeanDefinition#factoryBeanName 为 null 而不是配置类的名称
    // {@link AbstractApplicationContext.registerBeanPostProcessors} 会注册并创建所有的 BeanPostProcessor 对象
    // 在创建某个 Bean 时，会创建该 Bean 的 factoryBean
    // 此时要是没有添加 static，那么注册该 BeanPostProcessor 的时候会先创建 CustomizedAutowiredAnnotationDemo 类，
    // 此时因为 AutowiredAnnotationBeanPostProcessor 还没有创建，所以不会处理 CustomizedAutowiredAnnotationDemo 类中自动注入的属性
    // 总之，注册 BeanPostProcessor 的时候，一定要使其成为静态方法，那么 BeanDefinition#factoryBeanName 为 null 而不是配置类的名称 从而不会影响配置类的实例化

    @Bean(value = "org.springframework.context.annotation.internalAutowiredAnnotationProcessor")
    private static BeanPostProcessor beanPostProcessor() {
        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        // 这里因为会替换掉原有的 AutowiredAnnotationBeanPostProcessor 所以需要将所有的自动注入的注解都添加进去
        Set<Class<? extends Annotation>> autowiredAnnotationTypes = Stream.of(Autowired.class, MyAutowired.class, Inject.class, Value.class)
                .collect(Collectors.toSet());
        beanPostProcessor.setAutowiredAnnotationTypes(autowiredAnnotationTypes);
        return beanPostProcessor;
    }

    // 定义一个 {@link AutowiredAnnotationBeanPostProcessor} 去处理自定义注解，不影响原有的能力
    // @Bean
    private static BeanPostProcessor customizedAutowiredAnnotationProcessor() {
        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        beanPostProcessor.setAutowiredAnnotationType(MyAutowired.class);
        return beanPostProcessor;
    }

    public static void main(String[] args) {
        // 这里直接会默认添加内部的 BeanDefinition 到 BeanFactory#BeanDefinitionMap 中
        // @see AnnotationConfigUtils.registerAnnotationConfigProcessors
        // 常见的 AutowiredAnnotationBeanPostProcessor、CommonAnnotationBeanPostProcessor
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 这里会将指定的类转换成 BeanDefinition 保存在 BeanFactory#BeanDefinitionMap
        applicationContext.register(CustomizedAutowiredAnnotationDemo.class);
        // 初始化 BeanFactory
        applicationContext.refresh();

        CustomizedAutowiredAnnotationDemo demo = applicationContext.getBean(CustomizedAutowiredAnnotationDemo.class);
        System.out.println("demo.user = " + demo.user);
        System.out.println("demo.myUser = " + demo.myUser);

        applicationContext.close();
    }
}
