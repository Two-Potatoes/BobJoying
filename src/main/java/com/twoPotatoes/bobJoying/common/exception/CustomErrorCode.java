package com.twoPotatoes.bobJoying.common.exception;

import org.springframework.graphql.execution.ErrorType;

import lombok.Getter;

@Getter
public enum CustomErrorCode {
    TEAM_NOT_FOUND(ErrorType.NOT_FOUND, "팀이 존재하지 않습니다."),
    ACCOUNT_ALREADY_EXISTS(ErrorType.BAD_REQUEST, "이미 가입한 계정입니다.");

    private final ErrorType errorType;
    private final String message;

    CustomErrorCode(ErrorType errorType, String message) {
        this.errorType = errorType;
        this.message = message;
    }
}
