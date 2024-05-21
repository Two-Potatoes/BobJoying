package com.twoPotatoes.bobJoying.member.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Getter;

@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24 * 7)   // 일주일
@Getter
@AllArgsConstructor
public class RefreshToken {

    @Id
    private Integer MemberId;
    private String refreshToken;
}
