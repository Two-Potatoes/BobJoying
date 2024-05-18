package com.twoPotatoes.bobJoying.member.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
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
import com.twoPotatoes.bobJoying.member.dto.SignupRequestDto;
import com.twoPotatoes.bobJoying.member.entity.Member;
import com.twoPotatoes.bobJoying.member.entity.MemberRoleEnum;
import com.twoPotatoes.bobJoying.member.entity.Team;
import com.twoPotatoes.bobJoying.member.repository.MemberRepository;
import com.twoPotatoes.bobJoying.member.repository.TeamRepository;

// MemberService의 메서드를 테스트합니다.
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @Mock
    MemberRepository memberRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtUtil jwtUtil;
    @InjectMocks
    MemberServiceImpl memberService;

    @Captor
    ArgumentCaptor<Member> memberCaptor;

    SignupRequestDto signupRequestDto;

    LoginRequestDto loginRequestDto;

    @BeforeEach
    void setUp() {
        signupRequestDto = SignupRequestDto.builder()
            .email("test-email@email.com")
            .password("rightPassword123!")
            .nickname("test-nickname")
            .build();

        loginRequestDto = LoginRequestDto.builder()
            .email("test-email@email.com")
            .password("rightPassword123!")
            .build();
    }

    @Test
    @DisplayName("이미 같은 이메일로 가입한 유저가 있을 때")
    void signupAccountAlreadyExists() {
        // given
        given(memberRepository.findByEmail(anyString())).willReturn(Optional.of(new Member()));

        assertThrows(CustomException.class, () -> memberService.signup(signupRequestDto));
    }

    @Test
    @DisplayName("회원가입 성공")
    void signup() {
        // given
        given(memberRepository.findByEmail(anyString())).willReturn(Optional.empty());

        // when
        memberService.signup(signupRequestDto);

        // then
        then(memberRepository).should().save(memberCaptor.capture());
        Member member = memberCaptor.getValue();
        assertEquals(signupRequestDto.getEmail(), member.getEmail());
        assertEquals(signupRequestDto.getNickname(), member.getNickname());
        then(memberRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("로그인 실패 - 일치하는 이메일이 없을 때")
    void loginFailureByEmail() {
        // given
        given(memberRepository.findByEmail(anyString())).willReturn(Optional.empty());

        assertThrows(CustomException.class, () -> memberService.login(loginRequestDto));
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호가 일치하지 않을 때")
    void loginFailureByPassword() {
        // given
        Member member = Member.builder().password("test").build();
        given(memberRepository.findByEmail(anyString())).willReturn(Optional.of(member));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

        assertThrows(CustomException.class, () -> memberService.login(loginRequestDto));
    }

    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() {
        // given
        Member member = Member.builder()
            .email("test-email@email.com")
            .password("test-password")
            .role(MemberRoleEnum.MEMBER)
            .build();
        given(memberRepository.findByEmail(anyString())).willReturn(Optional.of(member));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
        given(jwtUtil.createToken(anyString(), any())).willReturn("exampleToken");

        // when
        String token = memberService.login(loginRequestDto);

        // then
        assertEquals(token, "exampleToken");
    }
}
