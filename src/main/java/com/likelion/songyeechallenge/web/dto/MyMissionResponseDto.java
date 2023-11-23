package com.likelion.songyeechallenge.web.dto;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.domain.mission.Mission;
import lombok.Getter;

@Getter
public class MyMissionResponseDto {

    private static String filePath = "./src/main/resources/static/images/";
    private Long mission_id;
    private String missionDate;
    private String missions;
    private boolean isComplete;
    private Long challenge_id;
    private String title;
    private String pictureName;

    public MyMissionResponseDto(Mission mission) {
        this.mission_id = mission.getMission_id();
        this.missionDate = mission.getMissionDate();
        this.missions = mission.getMission();
        this.isComplete = mission.isComplete();

        Challenge challenge = mission.getChallenge();
        this.challenge_id = challenge.getChallenge_id();
        this.title = challenge.getTitle() + " " + challenge.getCategory();
        this.pictureName = filePath + challenge.getPicture().getNewName();
    }
}
