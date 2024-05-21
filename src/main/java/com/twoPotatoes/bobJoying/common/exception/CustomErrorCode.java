package com.twoPotatoes.bobJoying.common.exception;

import org.springframework.graphql.execution.ErrorType;

import lombok.Getter;

@Getter
public enum CustomErrorCode {
    ACCOUNT_ALREADY_EXISTS(ErrorType.BAD_REQUEST, "이미 가입한 계정입니다."),
    INVALID_ACCESS(ErrorType.BAD_REQUEST, "잘못된 접근입니다."),
    MEMBER_NOT_FOUND(ErrorType.NOT_FOUND, "멤버가 존재하지 않습니다."),
    LOGIN_REQUEST(ErrorType.BAD_REQUEST, "다시 로그인해주세요.");

    private final ErrorType errorType;
    private final String message;

    CustomErrorCode(ErrorType errorType, String message) {
        this.errorType = errorType;
        this.message = message;
    }
}
