package com.likelion.songyeechallenge.web.dto;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.domain.mission.Mission;
import com.likelion.songyeechallenge.domain.user.User;
import com.likelion.songyeechallenge.service.PictureService;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ChallengeDetailResponseDto {
    private Long challenge_id;
    private String title;
    private String writer;
    private String startDate;
    private String endDate;
    private String category;
    private String explain;
    private List<MissionResponseDto> missions;
    private String filePath;
    private int participantsNumber;
    private double progressPercent;

    public ChallengeDetailResponseDto(Challenge entity, PictureService pictureService) {
        this.challenge_id = entity.getChallenge_id();
        this.title = entity.getTitle();
        this.writer = entity.getWriter();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.category = entity.getCategory();
        this.explain = entity.getExplain();
        this.missions = convertMissionDto(entity.getMissions());
        this.filePath = pictureService.getPictureUrl(entity);
        this.participantsNumber = entity.getParticipants().size();
        this.progressPercent = calculateProgress();
    }

    private List<MissionResponseDto> convertMissionDto(List<Mission> missions) {
        return missions.stream().map(mission -> new MissionResponseDto(mission.getMissionDate(), mission.getMission()))
                .collect(Collectors.toList());
    }

    private double calculateProgress() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        LocalDate startDate = LocalDate.parse(this.startDate, formatter);
        LocalDate endDate = LocalDate.parse(this.endDate, formatter);

        long totalDays = endDate.toEpochDay() - startDate.toEpochDay() + 1;
        long passedDays = today.toEpochDay() - startDate.toEpochDay() + 1;

        if (passedDays < 0) {
            return 0;
        }
        else if (passedDays > totalDays) {
            return 100;
        }
        else {
            double progress = (double) passedDays / totalDays * 100.0;
            return Math.round(progress * 100.0) / 100.0;
        }
    }
}
