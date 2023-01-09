package com.example.dorm.utils;


public enum ErrorCode {
    SUCCESS(200,"操作成功"),
    LOGIN_ERROR(510004,"用户名或密码错误"),
    REFRESH_ERROR(510005,"refresh_token过期"),
    Param_ERROR(513001,"楼号不存在")
    ;
    private final Integer code;
    private final String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
