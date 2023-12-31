package com.likelion.songyeechallenge.service;

import com.likelion.songyeechallenge.config.JwtTokenProvider;
import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.domain.challenge.ChallengeRepository;
import com.likelion.songyeechallenge.domain.like.Like;
import com.likelion.songyeechallenge.domain.like.LikeRepository;
import com.likelion.songyeechallenge.domain.review.Review;
import com.likelion.songyeechallenge.domain.review.ReviewRepository;
import com.likelion.songyeechallenge.domain.user.User;
import com.likelion.songyeechallenge.domain.user.UserRepository;
import com.likelion.songyeechallenge.web.dto.ReviewChallengeDto;
import com.likelion.songyeechallenge.web.dto.ReviewListResponseDto;
import com.likelion.songyeechallenge.web.dto.ReviewSaveRequestDto;
import com.likelion.songyeechallenge.web.dto.ReviewUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewService {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final ChallengeRepository challengeRepository;
    private final ReviewRepository reviewRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public List<ReviewChallengeDto> findMyChallenge(String jwtToken) {
        LocalDate today = LocalDate.now();
        String formattedToday = today.format(formatter);

        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        Set<Challenge> participatedChallenges = challengeRepository.findByMyChallengeAfterStart(userId, formattedToday);

        return participatedChallenges.stream()
                .map(ReviewChallengeDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Review postReview(ReviewSaveRequestDto requestDto, String jwtToken) {
        Review review = reviewRepository.save(requestDto.toEntity());

        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        User user = userRepository.findByUser_id(userId);
        review.setUser(user);

        String writer = jwtTokenProvider.getUserMajorFromToken(jwtToken) + " " + jwtTokenProvider.getUserNameFromToken(jwtToken);
        review.setWriter(writer);

        return reviewRepository.save(review);
    }

    @Transactional(readOnly = true)
    public List<ReviewListResponseDto> findAllReview(String jwtToken) {
        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        User user = userRepository.findByUser_id(userId);
        List<Long> likedReviewIdList = user.getLikes().stream()
                .map(Like::getReview)
                .map(Review::getReview_id)
                .collect(Collectors.toList());

        return reviewRepository.findAllDesc().stream()
                .map(review -> new ReviewListResponseDto(review, likedReviewIdList.contains(review.getReview_id())))
                .collect(Collectors.toList());
    }

    @Transactional
    public int pressLike(Long reviewId, String jwtToken) {
        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. id=" + reviewId));

        Like like = likeRepository.findByUserAndReview(userId, reviewId);

        if (like != null) {
            review.removeLike(like);
            likeRepository.delete(like);
            return 0;
        } else {
            like = Like.builder().user(userRepository.findByUser_id(userId)).review(review).build();
            review.addLike(like);
            likeRepository.save(like);
            return 1;
        }
    }

    public Long deleteReview(Long reviewId, String jwtToken) {
        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        Review review = reviewRepository.findByReviewIdAndUserId(reviewId, userId);
        reviewRepository.deleteById(review.getReview_id());
        return review.getReview_id();
    }

    @Transactional
    public Long updateReview(Long reviewId, ReviewUpdateRequestDto requestDto, String jwtToken) {
        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        Review review = reviewRepository.findByReviewIdAndUserId(reviewId, userId);
        review.update(requestDto.getContent());
        return reviewId;
    }
}
