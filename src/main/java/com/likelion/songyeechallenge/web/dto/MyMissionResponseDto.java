package com.likelion.songyeechallenge.web.dto;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.domain.userMission.UserMission;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MyMissionResponseDto {

    private Long challenge_id;
    private String challenge_title;
    private String explain;
    private String picture;
    private List<MyMissionCompleteDto> missions;
    private int missionCount;
    private int completedCount;

    public MyMissionResponseDto(Challenge entity, List<UserMission> userMissions) {
        this.challenge_id = entity.getChallenge_id();
        this.challenge_title = entity.getTitle() + " (" + entity.getCategory() + ")";
        this.explain = entity.getExplain();
        this.picture = entity.getPicture().getNewName();
        this.missions = entity.getMissions().stream()
                .map(mission -> new MyMissionCompleteDto(mission, userMissions))
                .collect(Collectors.toList());
        this.missionCount = entity.getMissions().size();
        this.completedCount = calcualteCompletedMission(userMissions);
    }

    private int calcualteCompletedMission(List<UserMission> userMissions) {
        return (int) userMissions.stream()
                .filter(UserMission::isComplete)
                .count();
    }
}
