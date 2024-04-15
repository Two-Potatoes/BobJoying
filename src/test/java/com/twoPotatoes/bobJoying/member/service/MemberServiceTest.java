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
import com.twoPotatoes.bobJoying.member.dto.SignupRequestDto;
import com.twoPotatoes.bobJoying.member.entity.Member;
import com.twoPotatoes.bobJoying.member.entity.Team;
import com.twoPotatoes.bobJoying.member.repository.MemberRepository;
import com.twoPotatoes.bobJoying.member.repository.TeamRepository;

// MemberService의 메서드를 테스트합니다.
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @Mock
    MemberRepository memberRepository;
    @Mock
    TeamRepository teamRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    MemberServiceImpl memberService;

    @Captor
    ArgumentCaptor<Member> memberCaptor;

    SignupRequestDto signupRequestDto;

    @BeforeEach
    void setUp() {
        signupRequestDto = SignupRequestDto.builder()
            .email("test-email@email.com")
            .password("rightPassword123!")
            .nickname("test-nickname")
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
        given(teamRepository.findById(anyInt())).willReturn(Optional.of(new Team()));

        // when
        memberService.signup(signupRequestDto);

        // then
        then(memberRepository).should().save(memberCaptor.capture());
        Member member = memberCaptor.getValue();
        assertEquals(signupRequestDto.getEmail(), member.getEmail());
        assertEquals(signupRequestDto.getNickname(), member.getNickname());
        then(memberRepository).shouldHaveNoMoreInteractions();
    }
}
