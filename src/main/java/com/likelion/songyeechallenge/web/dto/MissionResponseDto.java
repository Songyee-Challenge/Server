package com.likelion.songyeechallenge.web.dto;

import lombok.Getter;

@Getter
public class MissionResponseDto {
    private String missionDate;
    private String mission;

    public MissionResponseDto(String missionDate, String mission) {
        this.missionDate = missionDate;
        this.mission = mission;
    }
}
