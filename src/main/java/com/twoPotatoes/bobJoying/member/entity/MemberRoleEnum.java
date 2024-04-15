package com.twoPotatoes.bobJoying.member.entity;

public enum MemberRoleEnum {
    // 따로 "ROLE_"을 앞에 붙인 값을 저장해 주는 이유는
    // 기본적으로 Spring Security에서 권한을 인식할 때 "ROLE_" 접두사를 요구하기 때문입니다.
    MEMBER("ROLE_MEMBER"),   // 사용자 권한
    ADMIN("ROLE_ADMIN"); // 관리자 권한

    private final String authority;

    MemberRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

}
