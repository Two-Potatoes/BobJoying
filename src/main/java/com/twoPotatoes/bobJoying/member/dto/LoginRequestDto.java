package com.twoPotatoes.bobJoying.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginRequestDto {

    @NotBlank(message = "이메일 칸은 비울 수 없습니다.")
    private String email;

    @NotBlank(message = "비밀번호 칸은 비울 수 없습니다.")
    private String password;

}
