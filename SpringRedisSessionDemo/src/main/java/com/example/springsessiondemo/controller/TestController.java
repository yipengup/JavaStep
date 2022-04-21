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
@RequestMapping("/test")
public class TestController {

    @GetMapping("/test")
    public String test(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return session.getId();
    }

}
