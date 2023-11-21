package com.likelion.songyeechallenge.web.dto;

import lombok.Getter;

@Getter
public class ReviewChallengeDto {
    private String title;
    private String category;

    public ReviewChallengeDto(String title, String category) {
        this.title = title;
        this.category = category;
    }
}
