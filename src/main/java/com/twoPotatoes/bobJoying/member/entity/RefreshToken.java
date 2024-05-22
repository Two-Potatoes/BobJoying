package com.twoPotatoes.bobJoying.member.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import com.twoPotatoes.bobJoying.common.constants.AuthConstants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@RedisHash(value = "refreshToken", timeToLive = AuthConstants.REFRESH_TOKEN_TIME)
@Getter
@AllArgsConstructor
public class RefreshToken {

    @Id
    private Integer MemberId;
    private String refreshToken;
}
