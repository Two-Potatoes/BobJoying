package com.twoPotatoes.bobJoying.common.exception;

import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.twoPotatoes.bobJoying.common.constants.ErrorMsgConstants;

import graphql.GraphQLError;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

// 컨트롤러에서 예외 발생시 여기서 핸들링하여 errorCode와 메시지를 리턴합니다.
@ControllerAdvice
public class GlobalExceptionHandler {

    // CustomException 발생 시 작동하는 핸들러
    @GraphQlExceptionHandler
    public GraphQLError handleCustomException(CustomException ex) {
        return GraphQLError.newError()
            .errorType(ex.getErrorCode().getErrorType())
            .message(ex.getMessage())
            .build();
    }

    // validation 검사 실패 시 작동하는 핸들러
    @GraphQlExceptionHandler
    public GraphQLError handleValidation(ConstraintViolationException ex) {
        StringBuilder errorMessage = new StringBuilder();
        for (ConstraintViolation violation : ex.getConstraintViolations()) {
            errorMessage.append(violation.getMessage());
            break;
        }

        return GraphQLError.newError()
            .errorType(CustomErrorType.VALIDATION_ERROR)
            .message(errorMessage.toString())
            .build();
    }

    // 인증, 인가에 문제가 생겼을 시 작동하는 핸들러
    @GraphQlExceptionHandler
    public GraphQLError handleAccessDeniedException(AccessDeniedException ex) {
        return GraphQLError.newError()
            .errorType(CustomErrorType.UNAUTHORIZED)
            .message(ErrorMsgConstants.INVALID_ACCESS)
            .build();
    }

}
