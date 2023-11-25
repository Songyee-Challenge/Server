package com.likelion.songyeechallenge.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewUpdateRequestDto {

    private String title;
    private String content;

    public ReviewUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
