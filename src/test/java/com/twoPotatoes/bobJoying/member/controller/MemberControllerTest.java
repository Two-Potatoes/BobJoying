package com.twoPotatoes.bobJoying.member.controller;

import static org.mockito.BDDMockito.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import com.twoPotatoes.bobJoying.member.service.MemberService;

// MemberController를 테스트합니다.
@GraphQlTest(MemberController.class)
class MemberControllerTest {
    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("MemberController Test - 회원가입")
    void signup() {
        Map<String, Object> signupRequestDto = new HashMap<>();

        signupRequestDto.put("email", "test-email@email.com");
        signupRequestDto.put("password", "rightPassword123!");
        signupRequestDto.put("nickname", "test-nickname");

        graphQlTester.documentName("member")
            .variable("input", signupRequestDto)
            .operationName("signup")
            .execute()
            .path("signup.message")
            .entity(String.class)
            .isEqualTo("회원가입이 완료되었습니다.");
    }

    @Test
    @DisplayName("MemberController Test - 로그인")
    void login() {
        Map<String, Object> loginRequestDto = new HashMap<>();

        loginRequestDto.put("email", "test-email@email.com");
        loginRequestDto.put("password", "rightPassword123!");

        given(memberService.login(any())).willReturn("exampleToken");

        graphQlTester.documentName("member")
            .variable("input", loginRequestDto)
            .operationName("login")
            .execute()
            .path("login.message")
            .entity(String.class)
            .isEqualTo("로그인이 완료되었습니다.")
            .path("login.token")
            .entity(String.class)
            .isEqualTo("exampleToken");
    }
}
