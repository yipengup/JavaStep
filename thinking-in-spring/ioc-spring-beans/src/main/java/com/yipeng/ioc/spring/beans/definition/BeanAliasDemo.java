package com.yipeng.ioc.spring.beans.definition;

import com.yipeng.ioc.spring.beans.domain.User;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * Bean 别名示例
 * <p>
 * 其实 Spring Bean Alias 对应 {@link org.springframework.beans.factory.support.DefaultListableBeanFactory#aliasMap}
 * 其中存储的就是 AliasName => BeanName 的映射关系，当根据别名获取 Bean 时，实际上还是依赖原 BeanName 获取到对应的 Bean
 *
 * @author yipeng
 */
public class BeanAliasDemo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-alias-context.xml");
        // 查看简历别名后，IOC 容器里面存在多少个 User Bean
        Map<String, User> userMap = ((ListableBeanFactory) applicationContext).getBeansOfType(User.class);
        // userMap = {user=User(id=null, name=null)}
        // 可以看出来，IOC 容器里面只存在原 User Bean，不存在别名后的
        System.out.println("userMap = " + userMap);

        // 分别获取到 名称、别名对应的 Bean 信息，对比其是否相同
        User user = applicationContext.getBean("user", User.class);
        User userAlias = applicationContext.getBean("userAlias", User.class);
        System.out.println("别名后的 Bean 是否与原 Bean 相同：userAlias == user ：" + (userAlias == user));
    }
}
