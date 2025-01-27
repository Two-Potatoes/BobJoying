package com.twoPotatoes.bobJoying.member.service;

import com.twoPotatoes.bobJoying.common.dto.ApiResponseDto;
import com.twoPotatoes.bobJoying.member.dto.LoginRequestDto;
import com.twoPotatoes.bobJoying.member.dto.TokenRequestDto;
import com.twoPotatoes.bobJoying.member.dto.TokenResponseDto;
import com.twoPotatoes.bobJoying.member.entity.Member;

public interface AuthService {
    /**
     * 필요한 정보를 받아 인증하고 토큰을 발급합니다.
     *
     * @param loginRequestDto 인증에 필요한 유저 정보
     * @return 토큰 정보와 로그인 성공 메시지
     */
    TokenResponseDto login(LoginRequestDto loginRequestDto);

    /**
     * 만료된 Access Token, Refresh Token 을 받아 두 토큰 모두 재발급합니다.
     *
     * @param tokenRequestDto 토큰 재발급에 필요한 만료된 Access Token, Refresh Token
     * @return 새로운 Access Token, 새로운 Refresh Token
     */
    TokenResponseDto reissueToken(TokenRequestDto tokenRequestDto);

    /**
     * 인증된 사용자의 저장되어 있던 Refresh Token 을 삭제합니다.
     *
     * @param member 인증된 사용자
     * @return 로그아웃 성공 메시지
     */
    ApiResponseDto logout(Member member);
}
