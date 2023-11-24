package com.likelion.songyeechallenge.domain.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("SELECT l FROM Like l WHERE l.user.user_id = :userId AND l.review.review_id = :reviewId")
    Like findByUserAndReview(Long userId, Long reviewId);

    @Query("SELECT l FROM Like l WHERE l.user.user_id = :userId")
    Like findByUser(Long userId);

    @Query("SELECT l FROM Like l WHERE l.review.review_id = :reviewId")
    Like findByReview(Long reviewId);

}
