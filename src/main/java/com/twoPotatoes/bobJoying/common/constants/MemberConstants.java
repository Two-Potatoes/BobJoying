package com.twoPotatoes.bobJoying.common.constants;

public class MemberConstants {
    // Response Message
    public static final String SIGNUP_SUCCESS = "회원가입이 완료되었습니다.";
    public static final String LOGIN_SUCCESS = "로그인이 완료되었습니다.";
    public static final String REISSUE_TOKEN_SUCCESS = "토큰 재발급이 완료되었습니다.";
    public static final String LOGOUT_SUCCESS = "로그아웃이 완료되었습니다.";

    // Validation
    public static final String EMAIL_NOT_BLANK = "이메일 칸은 비울 수 없습니다.";
    public static final String INVALID_EMAIL = "올바른 이메일 형식이 아닙니다.";
    public static final String PASSWORD_NOT_BLANK = "비밀번호 칸은 비울 수 없습니다.";
    public static final String TOO_SHORT_PASSWORD = "비밀번호는 8자 이상이어야 합니다.";
    public static final String PASSWORD_REGULAR_EXPRESSION = "^(?=.*[a-z])(?=.*\\d)(?=.*[!?@#$%^&*_=+-]).+$";
    public static final String INVALID_PASSWORD = "비밀번호는 영소문자, 숫자, 특수문자(!,?,@,#,$,%,^,&,*,_,=,+,-)가 한 개 이상 포함되어야 합니다.";
    public static final String NICKNAME_NOT_BLANK = "닉네임 칸은 비울 수 없습니다.";
}
