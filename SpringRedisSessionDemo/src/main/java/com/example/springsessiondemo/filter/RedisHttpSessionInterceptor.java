package com.example.springsessiondemo.filter;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * redis session 缓存
 *
 * @author yipengup
 * @date 2021/9/25
 */
public class RedisHttpSessionInterceptor implements HandlerInterceptor {

    public static final String SESSION_KEY = "user";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("==============");
        HttpSession session = request.getSession();
        Object user = session.getAttribute(SESSION_KEY);
        if (Objects.nonNull(user)) {
            return true;
        }
        return false;
    }
}
