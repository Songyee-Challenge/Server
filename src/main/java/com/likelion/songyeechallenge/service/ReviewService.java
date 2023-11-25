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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewService {

    private LocalDate today = LocalDate.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private String formattedToday = today.format(formatter);

    private final ChallengeRepository challengeRepository;
    private final ReviewRepository reviewRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public List<ReviewChallengeDto> findMyChallenge(String jwtToken) {
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
    public List<ReviewListResponseDto> findAllReview() {
        return reviewRepository.findAllDesc().stream()
                .map(ReviewListResponseDto::new)
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

    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Transactional(readOnly = true)
    public boolean isReviewCreatedByUser(Long reviewId, String jwtToken) {
        Long userIdFromToken = jwtTokenProvider.getUserIdFromToken(jwtToken);
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);

        return optionalReview.map(review -> review.getUser().getUser_id().equals(userIdFromToken)).orElse(false);
    }

    @Transactional
    public Review updateReview(Long reviewId, ReviewUpdateRequestDto requestDto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. id=" + reviewId));

        // 리뷰를 작성한 사용자와 현재 로그인한 사용자가 일치하는지 확인
        if (!review.isCreatedByUser(jwtTokenProvider.getUserIdFromToken(requestDto.getToken()))) {
            throw new UnauthorizedException("리뷰를 수정할 권한이 없습니다.");
        }

        // 수정할 내용 업데이트
        review.update(requestDto.getTitle(), requestDto.getContent());

        return review;

    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String message) {
            super(message);
        }
    }
}
