package com.likelion.songyeechallenge.web.dto;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.domain.picture.Picture;
import com.likelion.songyeechallenge.service.PictureService;
import lombok.Getter;

@Getter
public class ChallengeListResponseDto {
    private Long challenge_id;
    private String title;
    private String category;
    private String startDate;
    private String endDate;
    private String explain;
    private String filePath;

    public ChallengeListResponseDto(Challenge entity, PictureService pictureService) {
        this.challenge_id = entity.getChallenge_id();
        this.title = entity.getTitle();
        this.category = entity.getCategory();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.explain = entity.getExplain();
        this.filePath = pictureService.getPictureUrl(entity);
    }
}
