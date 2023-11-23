package com.likelion.songyeechallenge.web.dto;

import com.likelion.songyeechallenge.domain.review.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewSaveRequestDto {

    private String title;
    private String myChallenge;
    private String content;
    private String writer;

    @Builder
    public ReviewSaveRequestDto(Review entity) {
        this.title = entity.getTitle();
        this.myChallenge = entity.getMyChallenge();
        this.content = entity.getContent();
        this.writer = entity.getWriter();
    }

    public Review toEntity() {
        return Review.builder()
                .title(title)
                .myChallenge(myChallenge)
                .content(content)
                .build();
    }

    @Override
    public String toString() {
        return "ReviewSaveRequestDto{" +
                "title='" + title + '\'' +
                ", myChallenge='" + myChallenge + '\'' +
                ", content='" + content + '\'' +
                ", writer='" + writer + '\'' +
                '}';
    }
}
