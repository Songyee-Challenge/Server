package com.likelion.songyeechallenge.controller;

import com.likelion.songyeechallenge.domain.review.Review;
import com.likelion.songyeechallenge.service.ReviewService;
import com.likelion.songyeechallenge.web.dto.ReviewChallengeDto;
import com.likelion.songyeechallenge.web.dto.ReviewListResponseDto;
import com.likelion.songyeechallenge.web.dto.ReviewSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/mychallenge")
    public List<ReviewChallengeDto> getMyChallenge(@RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        return reviewService.findMyChallenge(jwtToken);
    }

    @PostMapping("/post")
    public Long postReview(@RequestBody ReviewSaveRequestDto requestDto, @RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        Review review = reviewService.postReview(requestDto, jwtToken);
        return review.getReview_id();
    }

    @GetMapping("/all")
    public List<ReviewListResponseDto> getAllReview(@RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        return reviewService.findAllReview(jwtToken);
    }

    @PostMapping("/{id}/like")
    public int likeReview(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        return reviewService.pressLike(id, jwtToken);
    }
}
