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
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.twoPotatoes.bobJoying.common.config.GraphQlConfig;
import com.twoPotatoes.bobJoying.common.dto.ApiResponseDto;
import com.twoPotatoes.bobJoying.common.security.UserDetailsImpl;
import com.twoPotatoes.bobJoying.member.dto.TokenResponseDto;
import com.twoPotatoes.bobJoying.member.entity.Member;
import com.twoPotatoes.bobJoying.member.entity.MemberRoleEnum;
import com.twoPotatoes.bobJoying.member.service.AuthService;

@GraphQlTest(AuthController.class)
@Import(GraphQlConfig.class)
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

    @Test
    @DisplayName("AuthController Test - logout")
    void logout() {
        // Given
        Member member = Member.builder().role(MemberRoleEnum.MEMBER).build();
        UserDetailsImpl userDetails = new UserDetailsImpl(member);
        SecurityContextHolder
            .getContext()
            .setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
            );

        ApiResponseDto apiResponseDto = new ApiResponseDto("로그아웃 성공");
        given(authService.logout(any(Member.class))).willReturn(apiResponseDto);

        // When
        graphQlTester.documentName("auth")
            .operationName("logout")
            .execute()
            .path("logout.message")
            .entity(String.class);
    }
}
