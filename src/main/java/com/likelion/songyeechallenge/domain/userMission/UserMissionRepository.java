package com.likelion.songyeechallenge.domain.userMission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserMissionRepository extends JpaRepository<UserMission, Long> {

    @Query("SELECT um FROM UserMission um WHERE um.user.user_id = :userId AND um.mission.mission_id = :missionId")
    UserMission findMyMission(Long userId, Long missionId);
}
