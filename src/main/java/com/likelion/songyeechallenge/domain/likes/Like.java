package com.likelion.songyeechallenge.domain.likes;

import com.likelion.songyeechallenge.domain.review.Review;
import com.likelion.songyeechallenge.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heart_id")
    private Long heart_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Builder
    public Like(User user, Review review) {
        this.user = user;
        this.review = review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
