package com.likelion.songyeechallenge.web.dto;

import com.likelion.songyeechallenge.domain.mission.Mission;
import lombok.Getter;

@Getter
public class MissionResponseDto {
    private Long mission_id;
    private String missionDate;
    private String mission;

    public MissionResponseDto(Mission entity) {
        this.mission_id = entity.getMission_id();
        this.missionDate = entity.getMissionDate();
        this.mission = entity.getMission();
    }
}
