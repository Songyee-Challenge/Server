package com.likelion.songyeechallenge.service;

// import com.likelion.songyeechallenge.config.dto.SecurityUtil;
import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.domain.challenge.ChallengeRepository;
import com.likelion.songyeechallenge.domain.review.Review;
import com.likelion.songyeechallenge.domain.review.ReviewRepository;
import com.likelion.songyeechallenge.domain.user.User;
import com.likelion.songyeechallenge.web.dto.ReviewChallengeDto;
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

    private final ReviewRepository reviewRepository;
    private final ChallengeRepository challengeRepository;
    // private User user = SecurityUtil.getCurrentUser();

//    @Transactional
//    public List<ReviewChallengeDto> findMyChallenge() {
//        return reviewRepository.findByParticipants(user).stream()
//                .map(challenge -> new ReviewChallengeDto(challenge.getTitle(), challenge.getCategory()))
//                .collect(Collectors.toList());
//    }

    @Transactional
    public Review postReview(ReviewSaveRequestDto requestDto) {
        log.info("저장전 {}", requestDto);
        try {
            Review review = reviewRepository.save(requestDto.toEntity());
            log.info("저장된 requestDto: {}", review);
            return review;
        } catch (Exception e) {
            log.error("Review 저장 중 오류 발생", e);
            throw e; // 예외를 다시 던져서 호출자에게 전달
        }
    }
}
