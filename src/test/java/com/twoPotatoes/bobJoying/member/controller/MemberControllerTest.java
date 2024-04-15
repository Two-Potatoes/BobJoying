package com.twoPotatoes.bobJoying.member.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import com.twoPotatoes.bobJoying.member.service.MemberService;

@GraphQlTest(MemberController.class)
class MemberControllerTest {
    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private MemberService memberService;

    Map<String, Object> signupRequestDto;

    @BeforeEach
    void setUp() {
        signupRequestDto = new HashMap<>();

        signupRequestDto.put("email", "test-email@email.com");
        signupRequestDto.put("password", "rightPassword123!");
        signupRequestDto.put("nickname", "test-nickname");
    }

    @Test
    void signup() {
        graphQlTester.documentName("member/signup")
            .variable("input", signupRequestDto)
            .execute()
            .path("signup.message")
            .entity(String.class)
            .isEqualTo("회원가입이 완료되었습니다.");
    }
}
