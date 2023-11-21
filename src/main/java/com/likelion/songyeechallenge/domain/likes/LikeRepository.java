package com.likelion.songyeechallenge.domain.likes;

import com.likelion.songyeechallenge.domain.review.Review;
import com.likelion.songyeechallenge.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Like findByUserAndReview(User user, Review review);
}
