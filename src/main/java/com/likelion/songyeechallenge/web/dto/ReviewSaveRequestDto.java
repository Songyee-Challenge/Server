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

    @Builder
    public ReviewSaveRequestDto(String title, String myChallenge, String content) {
        this.title = title;
        this.myChallenge = myChallenge;
        this.content = content;
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
                '}';
    }
}
