package com.twoPotatoes.bobJoying.member.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.twoPotatoes.bobJoying.member.entity.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Integer> {
}
