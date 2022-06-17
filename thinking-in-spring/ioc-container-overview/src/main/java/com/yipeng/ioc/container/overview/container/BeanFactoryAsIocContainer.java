package com.yipeng.ioc.container.overview.container;

import com.yipeng.ioc.container.overview.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * 将 BeanFactory 作为 IOC 容器，不添加 ApplicationContext 其余的功能
 * @author yipeng
 */
public class BeanFactoryAsIocContainer {

    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 获取到读取 XML 配置文件的工具类，从而将 Bean 注册到 BeanFactory
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // 指定要加载 Bean 的 XML 文件的地址
        String location = "classpath:/META-INF/dependency-lookup-context.xml";
        // 读取并加载相应的 Bean 信息
        int beanDefinitionCount = reader.loadBeanDefinitions(location);
        System.out.println("加载自定义 Bean 的个数：beanDefinitionCount = " + beanDefinitionCount);

        // 加载完成之后就可以从 BeanFactory 中获取到注册的 Bean 信息
        User user = beanFactory.getBean(User.class);
        System.out.println("user = " + user);
    }
}
