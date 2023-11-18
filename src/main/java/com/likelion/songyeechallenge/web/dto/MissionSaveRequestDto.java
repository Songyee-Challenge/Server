package com.likelion.songyeechallenge.web.dto;

import com.likelion.songyeechallenge.domain.mission.Mission;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MissionSaveRequestDto {
    private String missionDate;
    private String mission;

    @Builder
    public MissionSaveRequestDto(String missionDate, String mission) {
        this.missionDate = missionDate;
        this.mission = mission;
    }

    public Mission toEntityMission() {
        return Mission.builder()
                .missionDate(missionDate)
                .mission(mission)
                .build();
    }
}
