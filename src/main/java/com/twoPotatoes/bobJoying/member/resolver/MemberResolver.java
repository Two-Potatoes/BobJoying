package com.twoPotatoes.bobJoying.member.resolver;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.twoPotatoes.bobJoying.common.dto.ApiResponseDto;
import com.twoPotatoes.bobJoying.member.dto.SignupRequestDto;
import com.twoPotatoes.bobJoying.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberResolver {
    private final MemberService memberService;

    @MutationMapping
    public ApiResponseDto signup(@Argument @Valid SignupRequestDto signupRequestDto) {
        memberService.signup(signupRequestDto);
        return new ApiResponseDto("회원가입이 완료되었습니다.");
    }
}
