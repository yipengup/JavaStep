package com.example.springsessiondemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author yipengup
 * @date 2021/9/24
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        System.out.println(request.getClass().getName());
        HttpSession session = request.getSession();
        session.setAttribute("user", "USER_INFO");
        return session.getId();
    }
}
