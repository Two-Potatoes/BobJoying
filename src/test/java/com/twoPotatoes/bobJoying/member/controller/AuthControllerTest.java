package com.twoPotatoes.bobJoying.member.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import com.twoPotatoes.bobJoying.member.dto.TokenResponseDto;
import com.twoPotatoes.bobJoying.member.service.AuthService;

@GraphQlTest(AuthController.class)
public class AuthControllerTest {
    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private AuthService authService;

    @Test
    @DisplayName("AuthController Test - login")
    void login() {
        Map<String, Object> loginRequestDto = new HashMap<>();

        loginRequestDto.put("email", "test-email@email.com");
        loginRequestDto.put("password", "rightPassword123!");

        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        String message = "로그인이 완료되었습니다.";
        TokenResponseDto tokenResponseDto = new TokenResponseDto(accessToken, refreshToken, message);
        given(authService.login(any())).willReturn(tokenResponseDto);

        graphQlTester.documentName("auth")
            .variable("input", loginRequestDto)
            .operationName("login")
            .execute()
            .path("login.accessToken")
            .entity(String.class)
            .isEqualTo(accessToken)
            .path("login.refreshToken")
            .entity(String.class)
            .isEqualTo(refreshToken);
    }

    @Test
    @DisplayName("AuthController Test - reissueToken")
    void reissueToken() {
        Map<String, Object> tokenRequestDto = new HashMap<>();

        tokenRequestDto.put("accessToken", "expiredAccessToken");
        tokenRequestDto.put("refreshToken", "oldRefreshToken");

        String newAccessToken = "newAccessToken";
        String newRefreshToken = "newRefreshToken";
        String message = "토큰 재발급이 완료되었습니다.";
        TokenResponseDto tokenResponseDto = new TokenResponseDto(newAccessToken, newRefreshToken, message);
        given(authService.reissueToken(any())).willReturn(tokenResponseDto);

        graphQlTester.documentName("auth")
            .variable("input", tokenRequestDto)
            .operationName("reissueToken")
            .execute()
            .path("reissueToken.accessToken")
            .entity(String.class)
            .isEqualTo(newAccessToken)
            .path("reissueToken.refreshToken")
            .entity(String.class)
            .isEqualTo(newRefreshToken);
    }
}
