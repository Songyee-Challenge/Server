//package com.likelion.songyeechallenge.web.dto;
//
//import com.likelion.songyeechallenge.domain.mission.Mission;
//import lombok.Getter;
//
//import java.util.List;
//
//@Getter
//public class MyMissionResponseDto {
//
//    private Long challenge_id;
//    private String missionDate;
//    private List<Mission> missions;
//    private boolean isComplete;
//
//    public MyMissionResponseDto(Mission mission) {
//        this.challenge_id = mission.getChallenge().getChallenge_id();
//        this.missionDate = mission.getMissionDate();
//        this.missions = mission.getMission();
//        this.isComplete = mission.isComplete();
//    }
//}
