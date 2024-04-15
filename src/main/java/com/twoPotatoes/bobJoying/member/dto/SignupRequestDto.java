package com.twoPotatoes.bobJoying.member.dto;

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
        message = "올바른 이메일 형식이 아닙니다."
    )
    @NotBlank(message = "이메일 칸은 비울 수 없습니다.")
    private String email;

    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[!?@#$%^&*_=+-]).+$",
        message = "비밀번호는 영소문자, 숫자, 특수문자(!,?,@,#,$,%,^,&,*,_,=,+,-)가 한 개 이상 포함되어야 합니다.")
    @NotBlank(message = "비밀번호 칸은 비울 수 없습니다.")
    private String password;

    @NotBlank(message = "닉네임 칸은 비울 수 없습니다.")
    private String nickname;

}
