package com.likelion.songyeechallenge.controller;

import com.likelion.songyeechallenge.domain.review.Review;
import com.likelion.songyeechallenge.service.ReviewService;
import com.likelion.songyeechallenge.web.dto.ReviewChallengeDto;
import com.likelion.songyeechallenge.web.dto.ReviewSaveRequestDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
@RestController
public class ReviewController {

    private final ReviewService reviewService;

//    @GetMapping("/mychallenge")
//    public List<ReviewChallengeDto> getMyChallenge() {
//        return reviewService.findMyChallenge();
//    }

    @PostMapping("/post")
    public Long postReview(ReviewSaveRequestDto requestDto) {
        Review review = reviewService.postReview(requestDto);
        return review.getReview_id();
    }
}
