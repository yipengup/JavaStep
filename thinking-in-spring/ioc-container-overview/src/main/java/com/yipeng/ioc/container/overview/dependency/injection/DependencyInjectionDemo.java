package com.yipeng.ioc.container.overview.dependency.injection;

import com.yipeng.ioc.container.overview.domain.User;
import com.yipeng.ioc.container.overview.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Collection;

/**
 * 依赖注入示例
 *
 * @author yipeng
 */
public class DependencyInjectionDemo {

    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");
        UserRepository userRepository = (UserRepository) beanFactory.getBean("userRepository");

        // 自动依赖注入
        injectByAutoWiring(userRepository);

        BeanFactory repositoryBeanFactory = userRepository.getBeanFactory();
        System.out.println("自动依赖注入内省Bean repositoryBeanFactory = " + repositoryBeanFactory);
        // 自动依赖注入的 Spring 内建依赖 Bean 与 beanFactory 不一致
        System.out.println(repositoryBeanFactory == beanFactory);

        // 此时从 BeanFactory 中获取不到 BeanFactory 这个Bean，但是依赖注入却是可以，证明依赖查找和依赖注入两种方式的依赖来源不同
        // BeanFactory beanFactoryBean = beanFactory.getBean(BeanFactory.class);
        // System.out.println("依赖查询 beanFactoryBean = " + beanFactoryBean);

        // 获取到上下文延迟自动依赖注入的 Bean
        ObjectFactory<ApplicationContext> contextObjectFactory = userRepository.getContextObjectFactory();
        // org.springframework.beans.factory.support.DefaultListableBeanFactory$DependencyObjectProvider
        System.out.println("contextObjectFactory = " + contextObjectFactory);
        // 获取到当前的上下文
        ApplicationContext applicationContext = contextObjectFactory.getObject();
        // 判断通过延迟注入的依赖是否与当前 beanFactory 上下文一致（结果一致）
        System.out.println("applicationContext == beanFactory : " + (applicationContext == beanFactory));


        // 依赖查找 容器内建Bean
        Environment environment = beanFactory.getBean(Environment.class);
        System.out.println("environment = " + environment);

    }

    private static void injectByAutoWiring(UserRepository userRepository) {
        Collection<User> userCollections = userRepository.getUserCollections();
        System.out.println("userCollections = " + userCollections);
    }


}
