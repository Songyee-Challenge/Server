package com.likelion.songyeechallenge.domain.review;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long review_id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String myChallenge;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @Builder
    public Review(String title, String myChallenge, String content) {
        this.title = title;
        this.myChallenge = myChallenge;
        this.content = content;
    }
}
