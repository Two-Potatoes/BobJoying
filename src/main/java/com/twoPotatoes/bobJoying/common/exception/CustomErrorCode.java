package com.twoPotatoes.bobJoying.common.exception;

import org.springframework.graphql.execution.ErrorType;

import com.twoPotatoes.bobJoying.common.constants.ErrorMsgConstants;

import lombok.Getter;

@Getter
public enum CustomErrorCode {
    ACCOUNT_ALREADY_EXISTS(ErrorType.BAD_REQUEST, ErrorMsgConstants.ACCOUNT_ALREADY_EXISTS),
    INVALID_ACCESS(ErrorType.BAD_REQUEST, ErrorMsgConstants.INVALID_ACCESS),
    MEMBER_NOT_FOUND(ErrorType.NOT_FOUND, ErrorMsgConstants.MEMBER_NOT_FOUND),
    LOGIN_REQUEST(ErrorType.BAD_REQUEST, ErrorMsgConstants.LOGIN_REQUEST),
    INGREDIENT_NOT_FOUND(ErrorType.NOT_FOUND, ErrorMsgConstants.INGREDIENT_NOT_FOUND),
    INVALID_QUANTITY(ErrorType.BAD_REQUEST, ErrorMsgConstants.INVALID_QUANTITY),
    INVALID_EXPIRATION_DATE(ErrorType.BAD_REQUEST, ErrorMsgConstants.INVALID_EXPIRATION_DATE),
    INVALID_UNIT(ErrorType.BAD_REQUEST, ErrorMsgConstants.INVALID_UNIT);

    private final ErrorType errorType;
    private final String message;

    CustomErrorCode(ErrorType errorType, String message) {
        this.errorType = errorType;
        this.message = message;
    }
}
