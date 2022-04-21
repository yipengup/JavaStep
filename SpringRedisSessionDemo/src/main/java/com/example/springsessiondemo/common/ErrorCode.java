package com.example.springsessiondemo.common;

import lombok.Getter;

@Getter
public enum ErrorCode {
    SYS_ERROR(8500, "系统错误"),

    SIGN_JWT_ERROR(8501, "获取token失败"),
    VERIFY_JWT_ERROR(8502, "校验token失败"),

    PARAM_INVALID(8400, "参数不合法"),

    EMPTY_USERNAME_OR_PASSWORD(8401, "用户名和密码不能为空"),

    INVALID_USERNAME_OR_PASSWORD(8402, "用户名或密码错误"),

    PASSWORD_ERROR(8403, "密码不正确"),

    DATA_NOT_FOUND(8404, "数据不存在"),

    REQUEST_REFER_ERROR(8601, "非法的请求来源"),
    BACKEND_SESSION_CHECK_ERROR(8602, "校验session失败"),

    ;

    private int code;
    private String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    ErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
