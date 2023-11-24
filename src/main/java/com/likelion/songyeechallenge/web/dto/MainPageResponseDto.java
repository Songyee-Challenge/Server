package com.likelion.songyeechallenge.web.dto;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import lombok.Getter;

@Getter
public class MainPageResponseDto {
    private Long challenge_id;
    private String picture;

    public MainPageResponseDto(Challenge entity) {
        this.challenge_id = entity.getChallenge_id();
        this.picture = entity.getPicture().getNewName();
    }
}
