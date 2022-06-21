package com.yipeng.ioc.spring.beans.definition.register;

import com.yipeng.ioc.spring.beans.domain.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 注册通过 XML 文件定义的 BeanDefinition
 *
 * @author yipeng
 */
public class ClasspathXmlBeanDefinitionRegistryDemo {

    public static void main(String[] args) {
        // 注册通过 XML 文件定义的 BeanDefinition
        // ClassPathXmlApplicationContext 通过 DefaultListableBeanFactory 去注册 BeanDefinition
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/META-INF/register-bean-definition-context.xml");
        User user = applicationContext.getBean(User.class);
        System.out.println("通过 XML 文件定义的 user = " + user);
    }

}
