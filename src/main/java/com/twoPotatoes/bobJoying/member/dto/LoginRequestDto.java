package com.twoPotatoes.bobJoying.member.dto;

import com.twoPotatoes.bobJoying.common.constants.MemberConstants;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginRequestDto {

    @NotBlank(message = MemberConstants.EMAIL_NOT_BLANK)
    private String email;

    @NotBlank(message = MemberConstants.PASSWORD_NOT_BLANK)
    private String password;

}
