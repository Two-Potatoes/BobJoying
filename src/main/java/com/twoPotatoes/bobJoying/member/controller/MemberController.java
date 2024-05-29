package com.twoPotatoes.bobJoying.member.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.twoPotatoes.bobJoying.common.constants.MemberConstants;
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
        return new ApiResponseDto(MemberConstants.SIGNUP_SUCCESS);
    }

    // TODO: Method마다 인가 거는 방법
    // @PreAuthorize("isAuthenticated()")
    // @Secured("ROLE_ADMIN")
}
