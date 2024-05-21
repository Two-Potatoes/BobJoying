package com.twoPotatoes.bobJoying.member.service;

import com.twoPotatoes.bobJoying.member.dto.LoginRequestDto;
import com.twoPotatoes.bobJoying.member.dto.TokenResponseDto;

public interface AuthService {
    /**
     * 필요한 정보를 받아 인증하고 토큰을 발급합니다.
     *
     * @param loginRequestDto 인증에 필요한 유저 정보
     * @return 토큰 정보와 로그인 성공 메시지
     */
    TokenResponseDto login(LoginRequestDto loginRequestDto);
}
