package com.twoPotatoes.bobJoying.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.twoPotatoes.bobJoying.common.exception.CustomErrorCode;
import com.twoPotatoes.bobJoying.common.exception.CustomException;
import com.twoPotatoes.bobJoying.common.security.JwtUtil;
import com.twoPotatoes.bobJoying.member.dto.LoginRequestDto;
import com.twoPotatoes.bobJoying.member.dto.TokenRequestDto;
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
        Member member;
        try {
            member = findMember(loginRequestDto.getEmail());
            checkPassword(loginRequestDto.getPassword(), member.getPassword());
        } catch (CustomException ce) {
            throw new CustomException(CustomErrorCode.INVALID_ACCESS);
        }

        String accessToken = jwtUtil.createAccessToken(member.getEmail(), member.getRole());
        String refreshToken = jwtUtil.createRefreshToken();

        RefreshToken memberToken = new RefreshToken(member.getId(), refreshToken);
        refreshTokenRepository.save(memberToken);

        return new TokenResponseDto(accessToken, refreshToken, "로그인이 완료되었습니다.");
    }

    @Override
    public TokenResponseDto reissueToken(TokenRequestDto tokenRequestDto) {
        String expiredAccessToken = tokenRequestDto.getAccessToken();
        String oldRefreshToken = tokenRequestDto.getRefreshToken();

        if (!jwtUtil.validateToken(oldRefreshToken)) {
            throw new CustomException(CustomErrorCode.LOGIN_REQUEST);
        }

        // 액세스 토큰으로부터 사용자 정보를 받는다.
        String email = jwtUtil.getUserEmailFromExpiredToken(expiredAccessToken);
        Member member = findMember(email);

        // 저장되어 있던 refresh token과 요청 정보에 있던 refresh token이 같은지 확인한다.
        RefreshToken foundRefreshToken = findRefreshToken(member.getId());
        if (!oldRefreshToken.equals(foundRefreshToken.getRefreshToken())) {
            throw new CustomException(CustomErrorCode.INVALID_ACCESS);
        }

        // 새로운 Access Token 과 Refresh Token 을 발급한다.
        String newRefreshToken = jwtUtil.createRefreshToken();
        String newAccessToken = jwtUtil.createAccessToken(email, member.getRole());
        refreshTokenRepository.save(new RefreshToken(member.getId(), newRefreshToken));
        return new TokenResponseDto(newAccessToken, newRefreshToken, "토큰 재발급이 완료되었습니다.");
    }

    @Override
    public String logout(Member member) {
        refreshTokenRepository.deleteById(member.getId());
        return "로그아웃이 완료되었습니다.";
    }

    private void checkPassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new CustomException(CustomErrorCode.INVALID_ACCESS);
        }
    }

    private RefreshToken findRefreshToken(Integer id) {
        return refreshTokenRepository.findById(id).orElseThrow(
            () -> new CustomException(CustomErrorCode.INVALID_ACCESS)
        );
    }

    private Member findMember(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
            () -> new CustomException(CustomErrorCode.MEMBER_NOT_FOUND)
        );
    }
}
