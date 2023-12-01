package com.likelion.songyeechallenge.domain.mission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    @Query("SELECT m FROM Mission m WHERE m.challenge.challenge_id = :challengeId")
    List<Mission> findByChallengeId(Long challengeId);

    @Query("SELECT m FROM Mission m WHERE m.mission_id = :missionId")
    Mission findByMissionId(Long missionId);
}
