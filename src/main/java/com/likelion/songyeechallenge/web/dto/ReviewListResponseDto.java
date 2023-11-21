package com.likelion.songyeechallenge.web.dto;

import com.likelion.songyeechallenge.domain.review.Review;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ReviewListResponseDto {
    private String title;
    private String myChallenge;
    private String content;
    private String writer;
    private String createdDate;
    private int likeCount;

    public ReviewListResponseDto(Review entity) {
        this.title = entity.getTitle();
        this.myChallenge = entity.getMyChallenge();
        this.content = entity.getContent();
        this.writer = entity.getWriter();
        this.createdDate = entity.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.likeCount = entity.getLikeCount();
    }
}
