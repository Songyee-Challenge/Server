package com.likelion.songyeechallenge.domain.missionUser;

import com.likelion.songyeechallenge.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MissionUserRepository extends JpaRepository<MissionUser, Long> {

    @Query("SELECT mu FROM MissionUser mu WHERE mu.user_id = :userId AND mu.mission_id = :missionId")
    MissionUser findMyMission(Long userId, Long missionId);
}
