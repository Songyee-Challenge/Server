package com.likelion.songyeechallenge.controller;

import com.likelion.songyeechallenge.service.MyPageService;
import com.likelion.songyeechallenge.service.ReviewService;
import com.likelion.songyeechallenge.web.dto.*;
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
    private final ReviewService reviewService;

    @GetMapping("/name")
    public String getMyName(@RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        return myPageService.findMyName(jwtToken);
    }

    @GetMapping("/challenge/recruiting/top2")
    public List<MyChallengeListResponseDto> getMyRecuritingTop2(@RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        return myPageService.findMyRecruitingTop2(jwtToken);
    }

    @GetMapping("/challenge/inprocess/top2")
    public List<MyChallengeListResponseDto> getMyInprocessTop2(@RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        return myPageService.findMyInProcessTop2(jwtToken);
    }

    @GetMapping("/challenge/finished/top2")
    public List<MyChallengeListResponseDto> getMyFinishedTop2(@RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        return myPageService.findMyFinishedTop2(jwtToken);
    }

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
    public List<ReviewListResponseDto> getMyReviews(@RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        return myPageService.findMyReview(jwtToken);
    }

    @DeleteMapping("/review/delete/{id}")
    public Long deleteReview(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        return reviewService.deleteReview(id, jwtToken);
    }

    @PutMapping("/review/edit/{id}")
    public Long updateReview(@PathVariable Long id, @RequestBody ReviewUpdateRequestDto requestDto, @RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        return reviewService.updateReview(id, requestDto, jwtToken);
    }

    @GetMapping("/mission")
    public List<MyMissionResponseDto> getUserMissions(@RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        return myPageService.findMyChallengeAndMission(jwtToken);
    }

    @PostMapping("/mission/{missionId}/{challengeId}")
    public boolean isCompleteMission(@PathVariable("missionId") Long missionId, @RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        return myPageService.isCompleteMission(missionId, jwtToken);
    }

    @GetMapping("/info")
    public UserInfoDto getUserInfo(@RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        return myPageService.findMyInfo(jwtToken);
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
}
