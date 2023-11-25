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
        this.isComplete = userMissions.stream().allMatch(UserMission::isComplete);
    }
}
