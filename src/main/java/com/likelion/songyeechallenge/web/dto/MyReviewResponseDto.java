package com.likelion.songyeechallenge.web.dto;

import com.likelion.songyeechallenge.domain.review.Review;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class MyReviewResponseDto {
    private String writtenDate;
    private String content;

    public MyReviewResponseDto(Review entity) {
        this.writtenDate = entity.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));;
        this.content = entity.getContent();
    }
}
