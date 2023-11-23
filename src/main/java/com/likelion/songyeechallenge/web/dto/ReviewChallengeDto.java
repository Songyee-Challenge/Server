package com.likelion.songyeechallenge.web.dto;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import lombok.Getter;

@Getter
public class ReviewChallengeDto {

    private String title;
    private String category;

    public ReviewChallengeDto(Challenge entity) {
        this.title = entity.getTitle();
        this.category = entity.getCategory();
    }
}
