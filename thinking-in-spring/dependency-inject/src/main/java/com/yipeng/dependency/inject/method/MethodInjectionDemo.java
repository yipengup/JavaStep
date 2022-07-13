package com.yipeng.dependency.inject.method;

import com.yipeng.common.domain.User;
import com.yipeng.common.domain.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 方法注入示例
 * 方法注入的方式如下：
 * {@link org.springframework.beans.factory.annotation.Autowired}
 * {@link javax.annotation.Resource}
 * {@link javax.annotation.Inject}
 * {@link org.springframework.context.annotation.Bean}
 * <p>
 * note: 对于方法注入的对应的方法的参数的个数没有限制，并且会依赖于 Spring 容器对参数进行自动注入。
 * 自动注入会先根据参数的名称进行依赖查找，没有找到的话，就再会根据参数的类型进行依赖查找
 * <p>
 * note：对于 {@link Autowired} 、{@link javax.annotation.Resource} 方法注入的话，方法必须具有参数，否则注入失败
 * 本质上，方法的注入依赖于对参数的依赖查找，然后作其他的逻辑处理，而不是说将当前方法会返回一个 Bean 对象保存到容器
 *
 * @author yipeng
 */
public class MethodInjectionDemo {

    private UserHolder userHolder;
    private UserHolder userHolder1;

    @Bean
    private User user() {
        return User.createUser("user");
    }

    @Bean
    @Primary
    private User user2() {
        return User.createUser("user2");
    }

    // @Resource 和 @Autowired 注解如果注解方法的话必须有参数
    // 其中方法名可以是任意（可以把setter方法注入看成是特殊的方法注入）
    @Autowired
    private void anyName(User user) {
        UserHolder userHolder = new UserHolder();
        userHolder.setUser(user);
        this.userHolder = userHolder;
    }

    @Resource
    private void anyName1(User userParam) {
        UserHolder userHolder = new UserHolder();
        userHolder.setUser(userParam);
        this.userHolder1 = userHolder;
    }


    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(MethodInjectionDemo.class);
        applicationContext.refresh();

        Map<String, User> userMap = applicationContext.getBeansOfType(User.class);
        userMap.forEach((id, user) -> System.out.printf("id = %s, user = %s\n", id, user));

        // 本例中容器里面不存在 UserHolder 类型的 Bean 
        Map<String, UserHolder> userHolderMap = applicationContext.getBeansOfType(UserHolder.class);
        userHolderMap.forEach((id, userHolder) -> System.out.printf("id = %s, userHolder = %s\n", id, userHolder));

        MethodInjectionDemo demo = applicationContext.getBean(MethodInjectionDemo.class);
        System.out.println("userHolder = " + demo.userHolder);
        System.out.println("userHolder1 = " + demo.userHolder1);

        applicationContext.close();
    }


}
