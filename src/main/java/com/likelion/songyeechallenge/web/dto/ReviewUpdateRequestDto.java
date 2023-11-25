package com.likelion.songyeechallenge.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewUpdateRequestDto {

    private String content;

    public ReviewUpdateRequestDto(String content) {
        this.content = content;
    }
}
