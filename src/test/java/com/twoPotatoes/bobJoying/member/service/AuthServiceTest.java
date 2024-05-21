package com.twoPotatoes.bobJoying.member.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.twoPotatoes.bobJoying.common.exception.CustomException;
import com.twoPotatoes.bobJoying.common.security.JwtUtil;
import com.twoPotatoes.bobJoying.member.dto.LoginRequestDto;
import com.twoPotatoes.bobJoying.member.dto.TokenRequestDto;
import com.twoPotatoes.bobJoying.member.dto.TokenResponseDto;
import com.twoPotatoes.bobJoying.member.entity.Member;
import com.twoPotatoes.bobJoying.member.entity.MemberRoleEnum;
import com.twoPotatoes.bobJoying.member.entity.RefreshToken;
import com.twoPotatoes.bobJoying.member.repository.MemberRepository;
import com.twoPotatoes.bobJoying.member.repository.RefreshTokenRepository;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    MemberRepository memberRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    RefreshTokenRepository refreshTokenRepository;
    @Mock
    JwtUtil jwtUtil;
    @InjectMocks
    AuthServiceImpl authService;
    @Captor
    ArgumentCaptor<RefreshToken> refreshTokenCaptor;
    static LoginRequestDto loginRequestDto;
    static TokenRequestDto tokenRequestDto;
    static String testRefreshToken = "testRefreshToken";
    static String expiredAccessToken = "expiredAccessToken";
    String testAccessToken = "testAccessToken";
    String newAccessToken = "newAccessToken";
    String newRefreshToken = "newRefreshToken";

    @BeforeAll
    static void beforeAll() {
        loginRequestDto = LoginRequestDto.builder()
            .email("test-email@email.com")
            .password("rightPassword123!")
            .build();
        tokenRequestDto = new TokenRequestDto();
        tokenRequestDto.setAccessToken(expiredAccessToken);
        tokenRequestDto.setRefreshToken(testRefreshToken);
    }

    @Test
    @DisplayName("로그인 실패 - 일치하는 이메일이 없을 때")
    void loginFailureByEmail() {
        // given
        given(memberRepository.findByEmail(anyString())).willReturn(Optional.empty());

        assertThrows(CustomException.class, () -> authService.login(loginRequestDto));
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호가 일치하지 않을 때")
    void loginFailureByPassword() {
        // given
        Member member = Member.builder().password("test").build();
        given(memberRepository.findByEmail(anyString())).willReturn(Optional.of(member));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

        assertThrows(CustomException.class, () -> authService.login(loginRequestDto));
    }

    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() {
        // given
        int memberId = 10;
        Member member = Member.builder()
            .id(memberId)
            .email("test-email@email.com")
            .password("test-password")
            .role(MemberRoleEnum.MEMBER)
            .build();
        given(memberRepository.findByEmail(anyString())).willReturn(Optional.of(member));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
        given(jwtUtil.createAccessToken(anyString(), any())).willReturn(testAccessToken);
        given(jwtUtil.createRefreshToken()).willReturn(testRefreshToken);

        // when
        TokenResponseDto tokenResponseDto = authService.login(loginRequestDto);

        // then
        then(refreshTokenRepository).should().save(refreshTokenCaptor.capture());
        RefreshToken refreshToken = refreshTokenCaptor.getValue();
        assertEquals(refreshToken.getMemberId(), 10);
        assertEquals(refreshToken.getRefreshToken(), testRefreshToken);
        assertEquals(tokenResponseDto.getAccessToken(), testAccessToken);
        assertEquals(tokenResponseDto.getRefreshToken(), testRefreshToken);
    }

    @Test
    @DisplayName("reissueToken 실패 - 요청받은 Refresh Token이 유효하지 않은 토큰일 때")
    void refreshTokenIsNotValid() {
        // given
        given(jwtUtil.validateToken(anyString())).willReturn(false);

        assertThrows(CustomException.class, () -> authService.reissueToken(tokenRequestDto));
    }

    @Test
    @DisplayName("reissueToken 실패 - Access Token에 담겨 있는 사용자 정보가 올바르지 않을 때")
    void accessTokenClaimIsNotValid() {
        // given
        given(jwtUtil.validateToken(anyString())).willReturn(true);
        String email = "test-email@email.com";
        given(jwtUtil.getUserEmailFromExpiredToken(anyString())).willReturn(email);
        given(memberRepository.findByEmail(email)).willReturn(Optional.empty());

        assertThrows(CustomException.class, () -> authService.reissueToken(tokenRequestDto));
    }

    @Test
    @DisplayName("reissueToken 실패 - 저장되어 있는 Refresh Token이 없을 때")
    void thereIsNoRefreshToken() {
        // given
        given(jwtUtil.validateToken(anyString())).willReturn(true);
        String email = "test-email@email.com";
        given(jwtUtil.getUserEmailFromExpiredToken(anyString())).willReturn(email);
        Member member = Member.builder().id(10).email(email).build();
        given(memberRepository.findByEmail(email)).willReturn(Optional.of(member));
        given(refreshTokenRepository.findById(member.getId())).willReturn(Optional.empty());

        assertThrows(CustomException.class, () -> authService.reissueToken(tokenRequestDto));
    }

    @Test
    @DisplayName("reissueToken 실패 - 저장되어 있던 Refresh Token과 요청 받은 Refresh Token이 다를 때")
    void differentRefreshToken() {
        // given
        given(jwtUtil.validateToken(anyString())).willReturn(true);
        String email = "test-email@email.com";
        given(jwtUtil.getUserEmailFromExpiredToken(anyString())).willReturn(email);
        Member member = Member.builder().id(10).email(email).build();
        given(memberRepository.findByEmail(email)).willReturn(Optional.of(member));
        RefreshToken refreshToken = new RefreshToken(member.getId(), "differentToken");
        given(refreshTokenRepository.findById(member.getId())).willReturn(Optional.of(refreshToken));

        assertThrows(CustomException.class, () -> authService.reissueToken(tokenRequestDto));
    }

    @Test
    @DisplayName("reissueToken 성공")
    void reissueRefreshToken() {
        // given
        given(jwtUtil.validateToken(anyString())).willReturn(true);
        String email = "test-email@email.com";
        given(jwtUtil.getUserEmailFromExpiredToken(anyString())).willReturn(email);
        Member member = Member.builder().id(10).email(email).role(MemberRoleEnum.MEMBER).build();
        given(memberRepository.findByEmail(email)).willReturn(Optional.of(member));
        RefreshToken refreshToken = new RefreshToken(10, testRefreshToken);
        given(refreshTokenRepository.findById(member.getId())).willReturn(Optional.of(refreshToken));
        given(jwtUtil.createRefreshToken()).willReturn(newRefreshToken);
        given(jwtUtil.createAccessToken(member.getEmail(), member.getRole())).willReturn(newAccessToken);

        // when
        TokenResponseDto tokenResponseDto = authService.reissueToken(tokenRequestDto);

        // then
        then(refreshTokenRepository).should().save(refreshTokenCaptor.capture());
        RefreshToken capturedRefreshToken = refreshTokenCaptor.getValue();
        assertEquals(capturedRefreshToken.getMemberId(), 10);
        assertEquals(capturedRefreshToken.getRefreshToken(), newRefreshToken);
        assertEquals(tokenResponseDto.getAccessToken(), newAccessToken);
        assertEquals(tokenResponseDto.getRefreshToken(), newRefreshToken);
    }

    @Test
    @DisplayName("logout 성공")
    void logout() {
        // Given
        int memberId = 10;
        Member member = Member.builder().id(memberId).build();

        // When
        String msg = authService.logout(member);

        // Then
        then(refreshTokenRepository).should().deleteById(memberId);
        then(refreshTokenRepository).shouldHaveNoMoreInteractions();
    }
}
