package com.arbiter.core.constant;

public enum ErrorCode {
    INVALID_MESSAGE_TYPE(4000, "잘못된 타입의 메시지를 변환/수정하려함."),
    COIN_PENDING_CREATION(4001, "아직 생성되지 않은 코인을 참조하려 함.");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
