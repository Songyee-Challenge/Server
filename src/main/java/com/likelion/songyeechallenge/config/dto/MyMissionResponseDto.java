package com.likelion.songyeechallenge.config.dto;

import com.likelion.songyeechallenge.domain.mission.Mission;
import com.likelion.songyeechallenge.service.PictureService;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyMissionResponseDto {

    private Long challengeId;
    private String challengeTitle;
    private String challengeImage;
    private String missionDate;
    private String mission;
    private boolean isComplete;

    public MyMissionResponseDto(Mission mission, PictureService pictureService) {
        this.challengeId = mission.getChallenge().getChallengeId();
        this.challengeTitle = mission.getChallenge().getTitle();
        this.challengeImage = pictureService.getPictureURL();
        this.missionDate = mission.getMissionDate();
        this.mission = mission.getMission();
        this.isComplete = mission.isComplete();
    }
}
