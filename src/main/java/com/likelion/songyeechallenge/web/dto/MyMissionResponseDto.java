package com.likelion.songyeechallenge.web.dto;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.domain.mission.Mission;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MyMissionResponseDto {

    private static String filePath = "src/main/resources/static/images/";
    private Long challenge_id;
    private String title;
    private String explain;
    private String picture;
    private List<MissionResponseDto> missions;
    private int missionCount;
    private int completedCount;

    public MyMissionResponseDto(Challenge entity) {
        this.challenge_id = entity.getChallenge_id();
        this.title = entity.getTitle() + " " + entity.getCategory();
        this.explain = entity.getExplain();
        this.picture = filePath + entity.getPicture().getNewName();
        this.missions = convertMissionDto(entity.getMissions());
        this.missionCount = entity.getMissions().size();
    }

    private List<MissionResponseDto> convertMissionDto(List<Mission> missions) {
        return missions.stream().map(MissionResponseDto::new)
                .collect(Collectors.toList());
    }
}
