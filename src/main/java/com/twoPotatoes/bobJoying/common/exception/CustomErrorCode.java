package com.twoPotatoes.bobJoying.common.exception;

import com.twoPotatoes.bobJoying.common.constants.ErrorMsgConstants;

import lombok.Getter;

@Getter
public enum CustomErrorCode {
    ACCOUNT_ALREADY_EXISTS(CustomErrorType.BAD_REQUEST, ErrorMsgConstants.ACCOUNT_ALREADY_EXISTS),
    INVALID_ACCESS(CustomErrorType.BAD_REQUEST, ErrorMsgConstants.INVALID_ACCESS),
    MEMBER_NOT_FOUND(CustomErrorType.NOT_FOUND, ErrorMsgConstants.MEMBER_NOT_FOUND),
    LOGIN_REQUEST(CustomErrorType.BAD_REQUEST, ErrorMsgConstants.LOGIN_REQUEST),
    INGREDIENT_NOT_FOUND(CustomErrorType.NOT_FOUND, ErrorMsgConstants.INGREDIENT_NOT_FOUND),
    INVALID_QUANTITY(CustomErrorType.BAD_REQUEST, ErrorMsgConstants.INVALID_QUANTITY),
    INVALID_EXPIRATION_DATE(CustomErrorType.BAD_REQUEST, ErrorMsgConstants.INVALID_EXPIRATION_DATE),
    INVALID_UNIT(CustomErrorType.BAD_REQUEST, ErrorMsgConstants.INVALID_UNIT),
    MY_INGREDIENT_NOT_FOUND(CustomErrorType.NOT_FOUND, ErrorMsgConstants.INGREDIENT_NOT_FOUND),
    TOO_LONG_EXPIRATION_DATE(CustomErrorType.BAD_REQUEST, ErrorMsgConstants.TOO_LONG_EXPIRATION_DATE),
    INVALID_SORTBY(CustomErrorType.BAD_REQUEST, ErrorMsgConstants.INVALID_SORTBY),
    NO_MORE_DATA(CustomErrorType.NOT_FOUND, ErrorMsgConstants.NO_MORE_DATA);

    private final CustomErrorType errorType;
    private final String message;

    CustomErrorCode(CustomErrorType errorType, String message) {
        this.errorType = errorType;
        this.message = message;
    }
}
