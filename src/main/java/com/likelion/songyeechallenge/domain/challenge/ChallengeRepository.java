package com.likelion.songyeechallenge.domain.challenge;

import com.likelion.songyeechallenge.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    @Query("SELECT c FROM Challenge c WHERE c.startDate > :today ORDER BY c.challenge_id DESC")
    List<Challenge> findBeforeStartDateDesc(String today);

    @Query("SELECT c FROM Challenge c WHERE c.startDate <= :today AND c.endDate >= :today ORDER BY c.challenge_id DESC")
    List<Challenge> findBetweenStartDateAndEndDateDesc(String today);

    @Query("SELECT c FROM Challenge c WHERE c.endDate < :today ORDER BY c.challenge_id DESC")
    List<Challenge> findAfterEndDateDesc(String today);

    @Query("SELECT c FROM Challenge c WHERE c.startDate <= :today AND c.endDate >= :today ORDER BY c.participants.size DESC")
    List<Challenge> findBetweenStartDateAndEndDateHot(String today);

    List<Challenge> findByCategory(String category);

    @Query("SELECT c FROM Challenge c WHERE c.title LIKE %:searchWord% OR c.category LIKE %:searchWord% ORDER BY c.challenge_id DESC")
    List<Challenge> findByTitleOrCategory(String searchWord);

    @Query("SELECT c FROM Challenge c JOIN c.participants p WHERE p.user_id = :userId")
    Set<Challenge> findByParticipants(@Param("userId") Long userId);

    // 예정된 챌린지
    @Query("SELECT c FROM Challenge c JOIN c.participants p WHERE p.user_id = :userId AND c.startDate > :today")
    Set<Challenge> findMyUpcomingChallenges(@Param("userId") Long userId, @Param("today") String today);


    @Query("SELECT DISTINCT c FROM Challenge c " +
            "LEFT JOIN c.participants p " +
            "WHERE (c.startDate > :formattedToday) " +
            "AND c.writer = :userEmail " +
            "ORDER BY c.challenge_id DESC")
    List<Challenge> findUpcomingChallengesByCreator(@Param("userEmail") String userEmail, @Param("formattedToday") String formattedToday);

    List<Challenge> findUpcomingChallengesByParticipant(String participantEmail, String today);

    List<Challenge> findOngoingChallenges(String userEmail, String today);

    List<Challenge> findFinishedChallenges(String userEmail, String today);

}
