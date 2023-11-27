package com.likelion.songyeechallenge.web.dto;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import lombok.Getter;

@Getter
public class MainPageResponseDto {
    private Long challenge_id;
    private String challenge_title;
    private String startDate;
    private String endDate;
    private String explain;
    private String picture;
    private int participantsNumber;

    public MainPageResponseDto(Challenge entity) {
        this.challenge_id = entity.getChallenge_id();
        this.challenge_title = entity.getTitle() + " (" + entity.getCategory() + ")";
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.explain = entity.getDetail();
        this.picture = entity.getPicture().getNewName();
        this.participantsNumber = entity.getParticipants().size();
    }
}
