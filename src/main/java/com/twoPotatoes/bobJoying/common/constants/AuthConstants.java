package com.twoPotatoes.bobJoying.common.constants;

public class AuthConstants {

    // JWT constants
    public static final String AUTHORIZATION_HEADER = "Authorization";       // Header KEY 값
    public static final String AUTHORIZATION_KEY = "auth";      // 사용자 권한 값의 KEY
    public static final String BEARER_PREFIX = "Bearer ";       // Token 식별자

    // 토큰 만료시간
    public static final long ACCESS_TOKEN_TIME = 30 * 60 * 1000L; // 30분
    public static final long REFRESH_TOKEN_TIME = 7 * 24 * 60 * 60 * 1000L; // 일주일
}
