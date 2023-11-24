package com.likelion.songyeechallenge.controller;

import com.likelion.songyeechallenge.config.JwtTokenProvider;
import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.domain.user.User;
import com.likelion.songyeechallenge.domain.user.UserRepository;
import com.likelion.songyeechallenge.service.MyPageService;
import com.likelion.songyeechallenge.service.ReviewService;
import com.likelion.songyeechallenge.web.dto.*;
import com.likelion.songyeechallenge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;
    private final UserService userService;
    private final ReviewService reviewService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

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

    @GetMapping("/info")
    public List<UserInfoDto> getUserInfo(@RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        return myPageService.showMyInfo(jwtToken);
    }


    @DeleteMapping("/delete-user")
    public ResponseEntity<String> deleteUser(@RequestHeader("Authorization") String authorizationHeader){
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        try {
            myPageService.deleteAccount(jwtToken);
            return ResponseEntity.ok("User account deleted successfully.");
        } catch (Exception e) {
            // 실패한 경우 예외를 캐치하여 실패 응답을 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user account.");
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

    @PostMapping("/mission/{missionId}/{challengeId}")
    public boolean isCompleteMission(@PathVariable("missionId") Long missionId, @PathVariable("challengeId") Long challengeId, @RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        return myPageService.isCompleteMission(missionId, challengeId, jwtToken);
    }
}
