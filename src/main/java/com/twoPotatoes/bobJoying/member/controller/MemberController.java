package com.twoPotatoes.bobJoying.member.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.twoPotatoes.bobJoying.common.dto.ApiResponseDto;
import com.twoPotatoes.bobJoying.member.dto.SignupRequestDto;
import com.twoPotatoes.bobJoying.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @MutationMapping
    public ApiResponseDto signup(@Argument @Valid SignupRequestDto signupRequestDto) {
        memberService.signup(signupRequestDto);
        return new ApiResponseDto("회원가입이 완료되었습니다.");
    }

    // TODO: 테스트로 만든 메서드들. 후에 삭제
    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public String onlyAuthenticated() {
        return "only Authenticated";
    }

    @QueryMapping
    @Secured("ROLE_ADMIN")
    public String onlyAdmin() {
        return "only Admin";
    }

    @QueryMapping
    public String everyone() {
        return "everyone";
    }
}
