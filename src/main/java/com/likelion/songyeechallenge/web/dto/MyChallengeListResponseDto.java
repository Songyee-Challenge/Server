package com.likelion.songyeechallenge.web.dto;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class MyChallengeListResponseDto {
    private Long challenge_id;
    private String challenge_title;
    private String startDate;
    private String endDate;
    private String detail;
    private double progressPercent;
    private String picture;
    private int myChallengeCount;

    public MyChallengeListResponseDto(Challenge entity, int myChallengeCount) {
        this.challenge_id = entity.getChallenge_id();
        this.challenge_title = entity.getTitle() + " (" + entity.getCategory() + ")";
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.detail = entity.getDetail();
        this.progressPercent = calculateProgress();
        this.picture = entity.getPicture().getNewName();
        this.myChallengeCount = myChallengeCount;
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
