package com.twoPotatoes.bobJoying.member.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.twoPotatoes.bobJoying.member.dto.SignupRequestDto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

// SignupRequestDto의 Validation을 Test합니다.
public class SignupRequestDtoValidationTest {

    private static Validator validator;

    private static SignupRequestDto signupRequestDto;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        signupRequestDto = SignupRequestDto.builder()
            .email("test@email.com")
            .password("tightPassword123!")
            .nickname("testNickname")
            .build();
    }

    @Test
    @DisplayName("email - Regular Expression")
    void emailPattern() {
        signupRequestDto.setEmail("wrongEmail");
        assertEquals("올바른 이메일 형식이 아닙니다.", getValidationExMessage());
    }

    @Test
    @DisplayName("email - Not Blank")
    void emailNotBlank() {
        signupRequestDto.setEmail("");
        assertEquals("이메일 칸은 비울 수 없습니다.", getValidationExMessage());
    }

    @Test
    @DisplayName("password - Size")
    void passwordSize() {
        signupRequestDto.setPassword("a12!");
        assertEquals("비밀번호는 8자 이상이어야 합니다.", getValidationExMessage());
    }

    @Test
    @DisplayName("password - Regular Expression - lower case")
    void passwordPattern1() {
        signupRequestDto.setPassword("12345678!");
        assertEquals(
            "비밀번호는 영소문자, 숫자, 특수문자(!,?,@,#,$,%,^,&,*,_,=,+,-)가 한 개 이상 포함되어야 합니다.",
            getValidationExMessage()
        );
    }

    @Test
    @DisplayName("password - Regular Expression - number")
    void passwordPattern2() {
        signupRequestDto.setPassword("abcdefg!");
        assertEquals(
            "비밀번호는 영소문자, 숫자, 특수문자(!,?,@,#,$,%,^,&,*,_,=,+,-)가 한 개 이상 포함되어야 합니다.",
            getValidationExMessage()
        );
    }

    @Test
    @DisplayName("password - Regular Expression - special character")
    void passwordPattern3() {
        signupRequestDto.setPassword("abcdefg1");
        assertEquals(
            "비밀번호는 영소문자, 숫자, 특수문자(!,?,@,#,$,%,^,&,*,_,=,+,-)가 한 개 이상 포함되어야 합니다.",
            getValidationExMessage()
        );
    }

    @Test
    @DisplayName("password - Not Blank")
    void passwordNotBlank() {
        signupRequestDto.setPassword(null);
        assertEquals("비밀번호 칸은 비울 수 없습니다.", getValidationExMessage());
    }

    @Test
    @DisplayName("nickname - Not Blank")
    void nicknameNotBlank() {
        signupRequestDto.setNickname("");
        assertEquals("닉네임 칸은 비울 수 없습니다.", getValidationExMessage());
    }

    private String getValidationExMessage() {
        Set<ConstraintViolation<SignupRequestDto>> validate = validator.validate(signupRequestDto);

        Iterator<ConstraintViolation<SignupRequestDto>> iter = validate.iterator();
        ConstraintViolation<SignupRequestDto> violation = iter.next();
        return violation.getMessage();
    }
}
