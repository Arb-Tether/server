package com.arbiter.core.exception;

import com.arbiter.core.constant.ErrorCode;
import lombok.Getter;


@Getter
public class InvalidMessageTypeException extends RuntimeException {
    private final ErrorCode errorCode;

    public InvalidMessageTypeException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public int getErrorCodeValue() {
        return errorCode.getCode();
    }
}
