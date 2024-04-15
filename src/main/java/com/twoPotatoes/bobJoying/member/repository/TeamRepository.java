package com.twoPotatoes.bobJoying.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twoPotatoes.bobJoying.member.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
}
