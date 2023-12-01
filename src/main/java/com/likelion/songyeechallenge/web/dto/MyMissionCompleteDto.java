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

    public MyMissionCompleteDto(UserMission userMission) {
        Mission missionEntity = userMission.getMission();
        this.mission_id = missionEntity.getMission_id();
        this.missionDate = missionEntity.getMissionDate();
        this.mission = missionEntity.getMission();
        this.isComplete = userMission.isComplete();
    }
}
