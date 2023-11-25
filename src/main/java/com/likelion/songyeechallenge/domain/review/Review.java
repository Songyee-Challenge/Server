package com.likelion.songyeechallenge.domain.review;

import com.likelion.songyeechallenge.domain.BaseTimeEntity;
import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.domain.like.Like;
import com.likelion.songyeechallenge.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long review_id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String myChallenge;

    @Column(nullable = false)
    private String content;

    private String writer;

    private int likeCount;

    @ManyToOne
    private Challenge challenge;

    @OneToMany(mappedBy = "review")
    private List<Like> likes = new ArrayList<>();

    @ManyToOne
    private User user;

    @Builder
    public Review(String title, String myChallenge, String content, String writer) {
        this.title = title;
        this.myChallenge = myChallenge;
        this.content = content;
        this.writer = writer;
        this.likeCount = 0;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addLike(Like like) {
        likes.add(like);
        like.setReview(this);
        increasedLikeCount();;
    }

    public void removeLike(Like like) {
        likes.remove(like);
        like.setReview(null);
        decreaseLikeCount();;
    }

    private void increasedLikeCount() {
        this.likeCount++;
    }

    private void decreaseLikeCount() {
        this.likeCount = Math.max(0, this.likeCount - 1);
    }

    public boolean isCreatedByUser(Long userId) {
        return this.user != null && this.user.getUser_id().equals(userId);
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
