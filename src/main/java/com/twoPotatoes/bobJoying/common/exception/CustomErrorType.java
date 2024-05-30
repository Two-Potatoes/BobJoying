package com.twoPotatoes.bobJoying.common.exception;

import graphql.ErrorClassification;
import graphql.PublicApi;

@PublicApi
public enum CustomErrorType implements ErrorClassification {
    VALIDATION_ERROR,
    BAD_REQUEST,
    NOT_FOUND,
    UNAUTHORIZED
}
