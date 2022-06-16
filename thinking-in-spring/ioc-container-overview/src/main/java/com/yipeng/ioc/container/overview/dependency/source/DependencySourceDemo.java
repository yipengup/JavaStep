package com.yipeng.ioc.container.overview.dependency.source;

import com.yipeng.ioc.container.overview.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

/**
 * 依赖来源
 * 1、自定义 Bean
 * 2、内建Bean
 * 3、内建依赖
 * @author yipeng
 */
public class DependencySourceDemo {
    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");

        // 依赖来源一（自定义Bean）
        UserRepository userRepository = beanFactory.getBean(UserRepository.class);
        System.out.println("自定义Bean userRepository = " + userRepository);

        // 依赖来源二（内建Bean）：可通过 beanFactory 直接获取到（依赖查找直接可以获取到）
        Environment environment = beanFactory.getBean(Environment.class);
        System.out.println("内建Bean environment = " + environment);

        // 依赖来源三（内建依赖）：依赖注入的时候可以自动注入，但是依赖查找获取不到
        BeanFactory repositoryBeanFactory = userRepository.getBeanFactory();
        System.out.println("内建依赖 repositoryBeanFactory = " + repositoryBeanFactory);
        // 依赖查找获取不到内建依赖（报错）
        // BeanFactory beanFactoryBean = beanFactory.getBean(BeanFactory.class);

    }
}
