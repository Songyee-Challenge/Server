package com.likelion.songyeechallenge.domain.challenge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    @Query("SELECT c FROM Challenge c WHERE c.startDate > CURRENT_DATE ORDER BY c.challenge_id DESC")
    List<Challenge> findBeforeStartDesc();

    @Query("SELECT c FROM Challenge c WHERE c.startDate <= CURRENT_DATE AND c.endDate >= CURRENT_DATE ORDER BY c.challenge_id DESC")
    List<Challenge> findInProcessDesc();

    @Query("SELECT c FROM Challenge c WHERE c.endDate < CURRENT_DATE ORDER BY c.challenge_id DESC")
    List<Challenge> findFinishedDesc();

    @Query("SELECT c FROM Challenge c WHERE c.startDate > CURRENT_DATE ORDER BY c.participants.size DESC")
    List<Challenge> findBeforeStartHot();

    @Query("SELECT c FROM Challenge c WHERE c.startDate > CURRENT_DATE ORDER BY c.startDate ASC")
    List<Challenge> findImminent();

    @Query("SELECT c FROM Challenge c WHERE c.category LIKE %:category% ORDER BY c.challenge_id DESC")
    List<Challenge> findByCategory(String category);

    @Query("SELECT c FROM Challenge c WHERE c.title LIKE %:searchWord% OR c.category LIKE %:searchWord% ORDER BY c.challenge_id DESC")
    List<Challenge> findByTitleOrCategory(String searchWord);

    @Query("SELECT c FROM Challenge c JOIN c.participants p WHERE p.user_id = :userId AND c.startDate <= CURRENT_DATE ORDER BY c.endDate ASC")
    Set<Challenge> findByMyChallengeAfterStart(@Param("userId") Long userId);

    @Query("SELECT c FROM Challenge c JOIN c.participants p WHERE p.user_id = :userId")
    Set<Challenge> findByParticipants(@Param("userId") Long userId);

    @Query("SELECT c FROM Challenge c WHERE c.challenge_id = :challengeId")
    Challenge findByChallenge_id(Long challengeId);

    @Query("SELECT c FROM Challenge c WHERE c.startDate <= CURRENT_DATE AND c.endDate >= CURRENT_DATE ORDER BY c.endDate ASC")
    List<Challenge> findInProcessEndingSoon();

    @Query("SELECT c FROM Challenge c WHERE c.endDate < CURRENT_DATE ORDER BY c.endDate DESC")
    List<Challenge> findJustFinished();
}
