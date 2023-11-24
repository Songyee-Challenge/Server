package com.likelion.songyeechallenge.web.dto;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import lombok.Getter;

@Getter
public class ReviewChallengeDto {

    private String challenge_title;

    public ReviewChallengeDto(Challenge entity) {
        this.challenge_title = entity.getTitle() + " (" + entity.getCategory() + ")";
    }
}
