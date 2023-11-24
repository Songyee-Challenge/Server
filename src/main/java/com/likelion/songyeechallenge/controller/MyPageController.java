package com.likelion.songyeechallenge.controller;

import com.likelion.songyeechallenge.domain.user.User;
import com.likelion.songyeechallenge.service.MyPageService;
import com.likelion.songyeechallenge.service.ReviewService;
import com.likelion.songyeechallenge.web.dto.ChallengeListResponseDto;
import com.likelion.songyeechallenge.web.dto.MyMissionResponseDto;
import com.likelion.songyeechallenge.web.dto.MyReviewResponseDto;
import com.likelion.songyeechallenge.service.UserService;
import com.likelion.songyeechallenge.web.dto.ReviewUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;
    private final UserService userService;
    private final ReviewService reviewService;

    @GetMapping("/challenge/recruiting")
    public List<ChallengeListResponseDto> getMyRecruiting(@RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        return myPageService.findMyRecruiting(jwtToken);
    }

    @GetMapping("/challenge/inprocess")
    public List<ChallengeListResponseDto> getMyInProcess(@RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        return myPageService.findMyInProcess(jwtToken);
    }

    @GetMapping("/challenge/finished")
    public List<ChallengeListResponseDto> getMyFinished(@RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        return myPageService.findMyFinished(jwtToken);
    }

    @GetMapping("/review")
    public List<MyReviewResponseDto> getMyReviews(@RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        return myPageService.findMyReview(jwtToken);
    }

    @GetMapping("/mission")
    public List<MyMissionResponseDto> getUserMissions(@RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        return myPageService.findMyChallengeAndMission(jwtToken);
    }

    @GetMapping("/info/{userId}")
    public ResponseEntity<User> getUserProfile(@PathVariable Long userId) {
        User userProfile = userService.getUserProfile(userId);
        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("User deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete user: " + e.getMessage());
        }
    }

    @DeleteMapping("/review/delete/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");

        if (reviewService.isReviewCreatedByUser(id, jwtToken)) {
            reviewService.deleteReview(id);
            return ResponseEntity.ok("Review deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to delete this review.");
        }
    }

    @PutMapping("/review/edit/{id}")
    public ResponseEntity<String> updateReview(@PathVariable Long id, @RequestBody ReviewUpdateRequestDto requestDto) {
        try {
            reviewService.updateReview(id, requestDto);
            return ResponseEntity.ok("Review updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ReviewService.UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update review: " + e.getMessage());
        }
    }
}
