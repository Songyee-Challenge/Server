package com.likelion.songyeechallenge.domain.likes;

import com.likelion.songyeechallenge.domain.review.Review;
import com.likelion.songyeechallenge.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("SELECT l FROM Like l WHERE l.user.user_id = :userId AND l.review.review_id = :reviewId")
    Like findByUserAndReview(Long userId, Long reviewId);
}
