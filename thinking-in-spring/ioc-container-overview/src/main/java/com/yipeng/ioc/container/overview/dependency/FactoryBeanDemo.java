package com.yipeng.ioc.container.overview.dependency;

import com.yipeng.ioc.container.overview.domain.User;
import com.yipeng.ioc.container.overview.domain.UserFactoryBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * FactoryBean 本身也是一个 Bean
 *
 * @author yipeng
 */
public class FactoryBeanDemo {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(FactoryBeanDemo.class);
        applicationContext.refresh();

        UserFactoryBean userFactoryBean = applicationContext.getBean(UserFactoryBean.class);
        System.out.println("userFactoryBean = " + userFactoryBean);
        User user = userFactoryBean.getObject();
        System.out.println("user = " + user);
    }


    @Bean
    public UserFactoryBean userFactoryBean() {
        return new UserFactoryBean();
    }
}
