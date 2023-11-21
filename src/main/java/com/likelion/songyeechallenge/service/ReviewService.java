package com.likelion.songyeechallenge.service;

import com.likelion.songyeechallenge.config.dto.SecurityUtil;
import com.likelion.songyeechallenge.domain.challenge.ChallengeRepository;
import com.likelion.songyeechallenge.domain.likes.Like;
import com.likelion.songyeechallenge.domain.likes.LikeRepository;
import com.likelion.songyeechallenge.domain.review.Review;
import com.likelion.songyeechallenge.domain.review.ReviewRepository;
import com.likelion.songyeechallenge.domain.user.User;
import com.likelion.songyeechallenge.web.dto.ReviewChallengeDto;
import com.likelion.songyeechallenge.web.dto.ReviewListResponseDto;
import com.likelion.songyeechallenge.web.dto.ReviewSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ChallengeRepository challengeRepository;
    private final ReviewRepository reviewRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public List<ReviewChallengeDto> findMyChallenge() {
        User user = SecurityUtil.getCurrentUser();
        return challengeRepository.findByParticipants(user).stream()
                .map(challenge -> new ReviewChallengeDto(challenge.getTitle(), challenge.getCategory()))
                .collect(Collectors.toList());
    }

    @Transactional
    public Review postReview(ReviewSaveRequestDto requestDto) {
        User user = SecurityUtil.getCurrentUser();
        return reviewRepository.save(requestDto.toEntity(user));
    }

    @Transactional(readOnly = true)
    public List<ReviewListResponseDto> findAllReview() {
        return reviewRepository.findAllDesc().stream()
                .map(ReviewListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public int pressLike(Long id) {
        User user = SecurityUtil.getCurrentUser();
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. id=" + id));

        Like like = likeRepository.findByUserAndReview(user, review);

        if(like != null) {
            review.removeLike(like);
            likeRepository.delete(like);
            return 0;
        }
        else {
            like = Like.builder().user(user).review(review).build();
            review.addLike(like);
            likeRepository.save(like);
            return 1;
        }
    }
}
