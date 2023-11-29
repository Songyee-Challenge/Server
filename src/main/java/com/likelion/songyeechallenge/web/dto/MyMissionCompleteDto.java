package com.likelion.songyeechallenge.web.dto;

import com.likelion.songyeechallenge.domain.mission.Mission;
import com.likelion.songyeechallenge.domain.userMission.UserMission;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

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
                .filter(um -> um.getMission() != null && um.getMission().getMission_id().equals(mission_id))
                .findFirst();

        this.isComplete = userMission.map(UserMission::isComplete).orElse(false);
    }
}
