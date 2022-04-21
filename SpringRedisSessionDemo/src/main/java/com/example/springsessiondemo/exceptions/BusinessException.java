package com.example.springsessiondemo.exceptions;

import com.example.springsessiondemo.common.ErrorCode;
import lombok.Getter;

/**
 * @author yipengup
 */
@Getter
public class BusinessException extends RuntimeException {
    private ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getDesc());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
