package com.twoPotatoes.bobJoying.member.service;

import com.twoPotatoes.bobJoying.member.dto.LoginRequestDto;
import com.twoPotatoes.bobJoying.member.dto.SignupRequestDto;

import jakarta.servlet.http.HttpServletResponse;

public interface MemberService {
    /**
     * 필요한 정보를 받아 유저 정보를 등록합니다.
     *
     * @param signupRequestDto 회원가입에 필요한 유저 정보
     */
    void signup(SignupRequestDto signupRequestDto);

    /**
     * 필요한 정보를 받아 인증하고 토큰을 발급합니다.
     *
     * @param loginRequestDto 인증에 필요한 유저 정보
     * @return JWT
     */
    String login(LoginRequestDto loginRequestDto);
}
