package com.example.springsessiondemo.controller;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 用于统一处理filter抛出的异常
 *
 * @author yipengup
 * @date 2021/9/25
 */
// @RestController
public class ErrorController extends BasicErrorController {

    public ErrorController() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
        ErrorProperties errorProperties = this.getErrorProperties();
        // 设置要包含异常信息，后面方便处理
        errorProperties.setIncludeMessage(ErrorProperties.IncludeAttribute.ALWAYS);
    }

    @Override
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        Map<String, Object> body = getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.ALL));
        Object message = body.get("message");
        return new ResponseEntity<>(body, status);
    }
}
