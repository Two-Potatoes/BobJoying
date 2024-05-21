package com.twoPotatoes.bobJoying.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequestDto {
    @NotBlank(message = "빈 값이 들어올 수 없습니다.")
    private String accessToken;
    @NotBlank(message = "빈 값이 들어올 수 없습니다.")
    private String refreshToken;
}
