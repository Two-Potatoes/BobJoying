package com.twoPotatoes.bobJoying.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.twoPotatoes.bobJoying.common.exception.CustomErrorCode;
import com.twoPotatoes.bobJoying.common.exception.CustomException;
import com.twoPotatoes.bobJoying.member.dto.SignupRequestDto;
import com.twoPotatoes.bobJoying.member.entity.Member;
import com.twoPotatoes.bobJoying.member.entity.MemberRoleEnum;
import com.twoPotatoes.bobJoying.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signup(SignupRequestDto signupRequestDto) {
        // 계정이 존재하는지 UK인 email로 확인합니다.
        if (findMember(signupRequestDto.getEmail()) != null) {
            throw new CustomException(CustomErrorCode.ACCOUNT_ALREADY_EXISTS);
        }

        // 계정이 존재하지 않으면 member에 default팀을 할당하여 유저 정보를 저장합니다.
        Member targetMember = Member.builder()
            .team(null)
            .email(signupRequestDto.getEmail())
            .password(passwordEncoder.encode(signupRequestDto.getPassword()))
            .nickname(signupRequestDto.getNickname())
            .role(MemberRoleEnum.MEMBER)
            .build();

        memberRepository.save(targetMember);
    }

    private Member findMember(String email) {
        return memberRepository.findByEmail(email).orElse(null);
    }
}
