package com.likelion.songyeechallenge.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r ORDER BY r.review_id DESC")
    List<Review> findAllDesc();

    @Query("SELECT r FROM Review r WHERE r.user.user_id = :userId")
    List<Review> findByUser(Long userId);

    @Query("SELECT r FROM Review r WHERE r.review_id = :reviewId AND r.user.user_id = :userId")
    Review findByReviewIdAndUserId(Long reviewId, Long userId);
}
