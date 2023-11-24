package com.likelion.songyeechallenge.web.dto;

// ReviewUpdateRequestDto.java

import lombok.Data;

@Data
public class ReviewUpdateRequestDto {

    private String title;
    private String content;
    private String token; // 사용자 인증 토큰
}
