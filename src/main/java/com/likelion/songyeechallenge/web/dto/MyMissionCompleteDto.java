package com.likelion.songyeechallenge.web.dto;

import com.likelion.songyeechallenge.domain.mission.Mission;
import com.likelion.songyeechallenge.domain.userMission.UserMission;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@Getter
public class MyMissionCompleteDto {

    private Long mission_id;
    private String missionDate;
    private String mission;
    private boolean isComplete;

    public MyMissionCompleteDto(Mission mission, List<UserMission> userMissions) {
        this.mission_id = mission.getMission_id();
        this.missionDate = mission.getMissionDate();
        this.mission = mission.getMission();

        Optional<UserMission> userMission = userMissions.stream()
                .filter(um -> {
                    boolean condition = um.getMission() != null && um.getMission().getMission_id().equals(mission_id);
                    if (!condition) {
                        log.info("미션이 비어있거나 유저미션과 일치하는 미션 아이디가 없습니다.");
                    }
                    return condition;
                })
                .findFirst();

        this.isComplete = userMission.map(UserMission::isComplete).orElse(false);
    }
}
