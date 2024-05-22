package com.twoPotatoes.bobJoying.member.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import com.twoPotatoes.bobJoying.common.security.UserDetailsImpl;
import com.twoPotatoes.bobJoying.member.dto.LoginRequestDto;
import com.twoPotatoes.bobJoying.member.dto.TokenRequestDto;
import com.twoPotatoes.bobJoying.member.dto.TokenResponseDto;
import com.twoPotatoes.bobJoying.member.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @MutationMapping
    public TokenResponseDto login(@Argument @Valid LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }

    @MutationMapping
    public TokenResponseDto reissueToken(@Argument @Valid TokenRequestDto tokenRequestDto) {
        return authService.reissueToken(tokenRequestDto);
    }

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public String logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return authService.logout(userDetails.getMember());
    }
}
