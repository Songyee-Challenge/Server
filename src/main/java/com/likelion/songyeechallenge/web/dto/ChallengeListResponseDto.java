package com.likelion.songyeechallenge.web.dto;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.service.PictureService;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class ChallengeListResponseDto {
    private Long challenge_id;
    private String title;
    private String category;
    private String startDate;
    private String endDate;
    private String explain;
    private double progressPercent;
    private String filePath;

    public ChallengeListResponseDto(Challenge entity, PictureService pictureService) {
        this.challenge_id = entity.getChallenge_id();
        this.title = entity.getTitle();
        this.category = entity.getCategory();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.explain = entity.getExplain();
        this.progressPercent = calculateProgress();
        this.filePath = pictureService.getPictureUrl(entity);
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
