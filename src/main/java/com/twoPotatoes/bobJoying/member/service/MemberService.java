package com.twoPotatoes.bobJoying.member.service;

import com.twoPotatoes.bobJoying.member.dto.SignupRequestDto;

public interface MemberService {
    /**
     * 필요한 정보를 받아 유저 정보를 등록합니다.
     *
     * @param signupRequestDto 회원가입에 필요한 유저 정보
     */
    void signup(SignupRequestDto signupRequestDto);
}
