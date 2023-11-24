package com.likelion.songyeechallenge.web.dto;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import lombok.Getter;

@Getter
public class ReviewChallengeDto {

    private Long challenge_id;
    private String challenge_title;

    public ReviewChallengeDto(Challenge entity) {
        this.challenge_id = entity.getChallenge_id();
        this.challenge_title = entity.getTitle() + " (" + entity.getCategory() + ")";
    }
}
