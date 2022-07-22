package com.yipeng.dependency.source;

import com.yipeng.common.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.ResourceLoader;

/**
 * 自定义 ResolvableDependency 添加到 IOC 容器中
 * <p>
 * 默认在在调用 {@link AbstractApplicationContext#refresh()} 过程中，获取到 BeanFactory 之后，
 * 调用 {@link AbstractApplicationContext#prepareBeanFactory(ConfigurableListableBeanFactory)} 对 BeanFactory 做一些初始化操作。
 * 其中默认会通过 {@link ConfigurableListableBeanFactory#registerResolvableDependency(Class, Object)} 方法注册，
 * {@link BeanFactory}、{@link ResourceLoader}、{@link ApplicationEventPublisher}、{@link ApplicationContext} 四个 ResolvableDependency，
 * 将其添加到容器 {@link DefaultListableBeanFactory#resolvableDependencies} 集合中
 * <p>
 * 在创建 Bean，对 Bean 的相关元素进行依赖注入的时候 {@link DefaultListableBeanFactory#findAutowireCandidates(String, Class, DependencyDescriptor)}
 * 如果对应的指定自动注入的元素满足 ResolvableDependency 的话，就会将其值自动注入。
 * <p>
 * note：
 * 1、通过 {@link ConfigurableListableBeanFactory#registerResolvableDependency(Class, Object)} 方法注册的依赖，
 * 不会被 {@link BeanFactory#getBean(String)} 依赖查找的方式获取到，只有上述自动注入的时候才会获取到。
 * 2、通过 {@link ConfigurableListableBeanFactory#registerResolvableDependency(Class, Object)} 方法注册的依赖，
 * 不会触发 Spring Bean 生命周期相关函数。
 * 3、因为 {@link DefaultListableBeanFactory#resolvableDependencies} 会参与到 Bean 初始化的自动注入的流程，
 * 所以一定要在 {@link AbstractApplicationContext#finishBeanFactoryInitialization(ConfigurableListableBeanFactory)} 方法执行前
 * 通过 {@link ConfigurableListableBeanFactory#registerResolvableDependency(Class, Object)}  将 ResolvableDependency 添加到 {@link DefaultListableBeanFactory#resolvableDependencies} ，
 * 所以可以通过调用 {@link AbstractApplicationContext#addBeanFactoryPostProcessor(BeanFactoryPostProcessor)} 添加 {@link BeanFactoryPostProcessor} 的方式，
 * 将 ResolvableDependency 添加到 {@link DefaultListableBeanFactory#resolvableDependencies}
 *
 * @author yipeng
 */
public class CustomizedResolvableDependencyDemo {

    // 通过自动注入的方式注入 ResolvableDependency 元素
    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    // 自动注入手动添加  ResolvableDependency
    @Autowired
    private User user;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 利用 BeanFactoryPostProcessor 将自定义的 ResolvableDependency 添加到 AbstractApplicationContext.beanFactoryPostProcessors 中
        applicationContext.addBeanFactoryPostProcessor(beanFactory -> beanFactory.registerResolvableDependency(User.class, User.createUser()));

        applicationContext.register(CustomizedResolvableDependencyDemo.class);
        applicationContext.refresh();

        CustomizedResolvableDependencyDemo demo = applicationContext.getBean(CustomizedResolvableDependencyDemo.class);
        System.out.println("demo.beanFactory = " + demo.beanFactory);
        System.out.println("demo.applicationContext = " + demo.applicationContext);
        System.out.println("demo.resourceLoader = " + demo.resourceLoader);
        System.out.println("demo.applicationEventPublisher = " + demo.applicationEventPublisher);
        System.out.println("demo.user = " + demo.user);

        applicationContext.close();
    }


}
