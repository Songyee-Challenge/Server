package com.likelion.songyeechallenge.web.dto;

import com.likelion.songyeechallenge.domain.review.Review;
import com.likelion.songyeechallenge.domain.user.User;
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
    public ReviewSaveRequestDto(String title, String myChallenge, String content, String writer) {
        this.title = title;
        this.myChallenge = myChallenge;
        this.content = content;
        this.writer = writer;
    }

    public Review toEntity(User user) {
        return Review.builder()
                .title(title)
                .myChallenge(myChallenge)
                .content(content)
                .writer(user.getName())
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
