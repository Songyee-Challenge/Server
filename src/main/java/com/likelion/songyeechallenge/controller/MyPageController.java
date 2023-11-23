package com.likelion.songyeechallenge.controller;

import com.likelion.songyeechallenge.service.MyPageService;
import com.likelion.songyeechallenge.web.dto.ChallengeListResponseDto;
import com.likelion.songyeechallenge.web.dto.MyReviewResponseDto;
import com.likelion.songyeechallenge.web.dto.ReviewListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

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

//    @GetMapping("/missions")
//    public List<MyMissionResponseDto> getUserMissions(@AuthenticationPrincipal JwtAuthenticationToken jwtAuthenticationToken) {
//        String jwtToken = jwtAuthenticationToken.getToken().getTokenValue();
//        return myPageService.getUserMissions(jwtToken);
//    }
}
