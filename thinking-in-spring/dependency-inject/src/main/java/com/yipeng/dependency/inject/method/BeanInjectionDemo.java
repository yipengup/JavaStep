package com.yipeng.dependency.inject.method;

import com.yipeng.common.domain.User;
import com.yipeng.common.domain.UserHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 利用 {@link org.springframework.context.annotation.Bean} 注解进行依赖注入示例
 * <p>
 * 总结：(Spring 的自动装配)不管是构造器参数注入、方法参数注入、属性注入，利用
 * {@link org.springframework.beans.factory.annotation.Autowired} 、{@link Bean}
 * 进行依赖注入的时候，都是先按照名称进行依赖查找，若是没有找到，再按照类型进行依赖查找
 *
 * @author yipeng
 */
public class BeanInjectionDemo {

    // 默认 {@Bean} 注解的 Bean 的 id 取得是方法名称
    // 可通过 {@Bean#value() 或者 @Bean#name()} 自定义 Bean 的 id
    @Bean
    public User user() {
        return User.createUser("userByBeanAnnotation");
    }

    @Bean
    public User userParam() {
        return User.createUser("userParam");
    }

    // 查看对于 {@Bean} 注入的 Bean 中对应的方法参数中查找依赖的方法：是按照参数名称查找还是按照方法参数查找
    // 方法即使是非 public 也能被识别
    @Bean
    private UserHolder userHolder(User userParam) {
        UserHolder userHolder = new UserHolder();
        userHolder.setUser(userParam);
        return userHolder;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanInjectionDemo.class);
        applicationContext.refresh();

        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        System.out.println("beanFactory = " + beanFactory);

        UserHolder userHolder = beanFactory.getBean(UserHolder.class);
        // userHolder = UserHolder(user=User(id=2, name=userParam))
        System.out.println("userHolder = " + userHolder);

        applicationContext.close();
    }
}
