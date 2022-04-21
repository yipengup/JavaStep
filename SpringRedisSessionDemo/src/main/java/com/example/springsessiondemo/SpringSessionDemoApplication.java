package com.example.springsessiondemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.FlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author Administrator
 */
@SpringBootApplication
@EnableRedisHttpSession(
        maxInactiveIntervalInSeconds = 60,
        redisNamespace = "backend:session",
        flushMode = FlushMode.ON_SAVE
)
public class SpringSessionDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSessionDemoApplication.class, args);
    }

}
