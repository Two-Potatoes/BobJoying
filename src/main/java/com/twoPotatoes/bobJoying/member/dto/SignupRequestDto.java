package com.twoPotatoes.bobJoying.member.dto;

import com.twoPotatoes.bobJoying.common.constants.MemberConstants;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignupRequestDto {
    @Email(
        regexp = "^.+@.+\\..+$",
        message = MemberConstants.INVALID_EMAIL
    )
    @NotBlank(message = MemberConstants.EMAIL_NOT_BLANK)
    private String email;

    @Size(min = 8, message = MemberConstants.TOO_SHORT_PASSWORD)
    @Pattern(
        regexp = MemberConstants.PASSWORD_REGULAR_EXPRESSION,
        message = MemberConstants.INVALID_PASSWORD)
    @NotBlank(message = MemberConstants.PASSWORD_NOT_BLANK)
    private String password;

    @NotBlank(message = MemberConstants.NICKNAME_NOT_BLANK)
    private String nickname;

}
