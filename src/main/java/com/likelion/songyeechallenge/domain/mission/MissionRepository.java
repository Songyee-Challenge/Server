package com.likelion.songyeechallenge.domain.mission;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    @Query("SELECT m FROM Mission m WHERE m.challenge IN (SELECT c FROM Challenge c JOIN c.participants p WHERE p.email = :userEmail)")
    List<Mission> findByChallengeParticipant(@Param("userEmail") String userEmail);

    @Query("SELECT m FROM Mission m WHERE m.challenge.challenge_id = :challengeId")
    List<Mission> findByChallengeId(Long challengeId);

    @Query("SELECT m FROM Mission m WHERE m.user.user_id = :userId AND m.mission_id = :missionId AND m.challenge.challenge_id = :challengeId")
    Mission findMyMissionCompleteness(Long userId, Long missionId, Long challengeId);
}
