package com.twoPotatoes.bobJoying.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.twoPotatoes.bobJoying.common.exception.CustomErrorCode;
import com.twoPotatoes.bobJoying.common.exception.CustomException;
import com.twoPotatoes.bobJoying.common.security.JwtUtil;
import com.twoPotatoes.bobJoying.member.dto.LoginRequestDto;
import com.twoPotatoes.bobJoying.member.dto.TokenResponseDto;
import com.twoPotatoes.bobJoying.member.entity.Member;
import com.twoPotatoes.bobJoying.member.entity.RefreshToken;
import com.twoPotatoes.bobJoying.member.repository.MemberRepository;
import com.twoPotatoes.bobJoying.member.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    @Override
    public TokenResponseDto login(LoginRequestDto loginRequestDto) {
        Member member = findMember(loginRequestDto.getEmail());
        if (member == null
            || !passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
            throw new CustomException(CustomErrorCode.INVALID_ACCESS);
        }
        String accessToken = jwtUtil.createAccessToken(member.getEmail(), member.getRole());
        String refreshToken = jwtUtil.createRefreshToken();

        RefreshToken memberToken = new RefreshToken(member.getId(), refreshToken);
        refreshTokenRepository.save(memberToken);

        return new TokenResponseDto(accessToken, refreshToken, "로그인이 완료되었습니다.");
    }

    private Member findMember(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
            () -> new CustomException(CustomErrorCode.MEMBER_NOT_FOUND)
        );
    }
}
