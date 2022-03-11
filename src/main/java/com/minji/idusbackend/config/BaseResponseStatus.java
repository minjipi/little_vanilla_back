package com.minji.idusbackend.config;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    SUCCESS(true,1000,"요청 성공."),
    FAIL(false,2000,"실패.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
