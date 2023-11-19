package com.likelion.songyeechallenge.web.dto;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.service.PictureService;
import lombok.Getter;

@Getter
public class MainPageResponseDto {
    private Long challenge_id;
    private String filePath;

    public MainPageResponseDto(Challenge entity, PictureService pictureService) {
        this.challenge_id = entity.getChallenge_id();
        this.filePath = pictureService.getPictureUrl(entity);
    }
}
