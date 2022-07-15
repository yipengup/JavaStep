package com.yipeng.dependency.inject.lazy;

import com.yipeng.common.domain.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.Collection;

/**
 * 延迟注入示例
 * {@link org.springframework.beans.factory.ObjectFactory}
 * {@link org.springframework.beans.factory.ObjectProvider}
 * <p>
 * {@link ObjectProvider} 是 {@link ObjectFactory} 的子接口，所以功能上是一样的
 * 既可以注入单个 Bean 又可以注入 集合 Bean。
 * 不同之处在于 ObjectProvider 提供了更丰富的方法去封装了一些异常处理，结合 Optional操作 更加的安全。
 * <p>
 * note：
 * 通过 {@link Autowired} 注解进行对 {@link ObjectProvider} 、{@link ObjectFactory} 类型进行注入时，
 * 其实现类都是 {@link DefaultListableBeanFactory.DependencyObjectProvider}
 *
 * @author yipeng
 */
public class LazyInjectorDemo {

    @Autowired
    private ObjectFactory<User> userObjectFactory;

    @Autowired
    private ObjectFactory<Collection<User>> usersObjectFactory;

    @Autowired
    private ObjectProvider<User> userObjectProvider;

    @Autowired
    private ObjectProvider<Collection<User>> usersObjectProvider;

    @Bean
    public User user1() {
        return User.createUser("user1");
    }

    @Bean
    @Primary
    public User user2() {
        return User.createUser("user2");
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(LazyInjectorDemo.class);
        applicationContext.refresh();

        LazyInjectorDemo demo = applicationContext.getBean(LazyInjectorDemo.class);
        // 查看自动注入后的对象类型
        // 都是 org.springframework.beans.factory.support.DefaultListableBeanFactory$DependencyObjectProvider
        System.out.println("demo.userObjectFactory.class = " + demo.userObjectFactory.getClass());
        System.out.println("demo.userObjectProvider.class = " + demo.userObjectProvider.getClass());


        System.out.println("demo.userObjectFactory = " + demo.userObjectFactory.getObject());
        System.out.println("demo.usersObjectFactory = " + demo.usersObjectFactory.getObject());
        System.out.println("demo.userObjectProvider = " + demo.userObjectProvider.getObject());
        System.out.println("demo.usersObjectProvider = " + demo.usersObjectProvider.getObject());

        applicationContext.close();
    }

}
