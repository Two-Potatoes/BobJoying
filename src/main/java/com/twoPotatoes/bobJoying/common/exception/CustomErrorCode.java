package com.twoPotatoes.bobJoying.common.exception;

import org.springframework.graphql.execution.ErrorType;

import lombok.Getter;

@Getter
public enum CustomErrorCode {
    ACCOUNT_ALREADY_EXISTS(ErrorType.BAD_REQUEST, "이미 가입한 계정입니다."),
    INVALID_ACCESS(ErrorType.BAD_REQUEST, "잘못된 접근입니다.");

    private final ErrorType errorType;
    private final String message;

    CustomErrorCode(ErrorType errorType, String message) {
        this.errorType = errorType;
        this.message = message;
    }
}
