package com.twoPotatoes.bobJoying.member.dto;

import com.twoPotatoes.bobJoying.common.constants.CommonValidationConstants;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequestDto {
    @NotBlank(message = CommonValidationConstants.BLANK_NOT_ALLOWED)
    private String accessToken;
    @NotBlank(message = CommonValidationConstants.BLANK_NOT_ALLOWED)
    private String refreshToken;
}
