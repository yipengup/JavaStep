package com.yipeng.dependency.lookup.hierarchical;

import com.yipeng.common.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Map;

/**
 * 分层 IOC 容器，类似于双亲委派的类加载器，当前容器存在父类容器
 * {@link org.springframework.beans.factory.HierarchicalBeanFactory}
 * {@link org.springframework.beans.factory.config.ConfigurableBeanFactory} 实现了分层容器的同时，还具有单利 Bean 注册器的功能
 * {@link org.springframework.beans.factory.config.ConfigurableListableBeanFactory} 可以理解为 ConfigurableBeanFactory + ListableBeanFactory
 * <p>
 * 可以利用 {@link org.springframework.beans.factory.config.ConfigurableBeanFactory#setParentBeanFactory(BeanFactory)} 为当前容器设置父容器
 * <p>
 * 获取到当前容器以及父容器所有的指定的 Bean
 * {@link org.springframework.beans.factory.BeanFactoryUtils#beansOfTypeIncludingAncestors(ListableBeanFactory, Class, boolean, boolean)}
 * {@link BeanFactoryUtils#beanNamesForAnnotationIncludingAncestors(ListableBeanFactory, Class)} 根据注解获取到所有容器中包含指定注解的 BeanNames
 *
 * @author yipeng
 */
public class HierarchicalBeanFactoryDemo {

    @Bean(value = "user")
    public User user() {
        return User.createUser();
    }

    public static void main(String[] args) {

        // 1. 创建容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(HierarchicalBeanFactoryDemo.class);
        applicationContext.refresh();

        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        Map<String, User> userMap = beanFactory.getBeansOfType(User.class);
        System.out.println("pre set parent beanFactory , beanFactory userMap = " + userMap);

        // 2. 依据 XML 文件创建容器
        DefaultListableBeanFactory parentBeanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(parentBeanFactory);
        reader.loadBeanDefinitions("classpath:/hierarchical-bean-factory-context.xml");
        Map<String, User> parentUserMap = parentBeanFactory.getBeansOfType(User.class);
        System.out.println("pre set parent beanFactory , parentBeanFactory parentUserMap = " + parentUserMap);

        // 3.设置父类容器
        beanFactory.setParentBeanFactory(parentBeanFactory);
        userMap = beanFactory.getBeansOfType(User.class);
        System.out.println("post set parent beanFactory , beanFactory userMap = " + userMap);

        // 4.利用 BeanFactoryUtils 打印所有的 User 相关信息
        // note: 父子容器中相同的 beanName，父容器不会覆盖掉子容器，结果以子容器为主
        userMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(beanFactory, User.class);
        System.out.println("beanFactory userMap = " + userMap);

        applicationContext.close();
    }
}
