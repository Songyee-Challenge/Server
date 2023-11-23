package com.likelion.songyeechallenge.controller;

import com.likelion.songyeechallenge.config.JwtTokenProvider;
import com.likelion.songyeechallenge.config.dto.MyChallengeListResponseDto;
import com.likelion.songyeechallenge.service.ChallengeService;
import com.likelion.songyeechallenge.service.MyPageService;
import com.likelion.songyeechallenge.web.dto.ChallengeListResponseDto;
import com.likelion.songyeechallenge.web.dto.ReviewListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.likelion.songyeechallenge.config.JwtTokenProvider;

import java.util.List;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/v1/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;
    private final ChallengeService challengeService;
    private JwtTokenProvider jwtTokenProvider;
    private String jwtToken;
    String userEmail = jwtTokenProvider.getUserEmailFromToken(jwtToken);

    @GetMapping("/upcoming")
    public List<ChallengeListResponseDto> getUpcomingChallenge() {
        MyChallengeListResponseDto myChallengeListResponseDto = myPageService.getMyChallenges(userEmail);
        return myChallengeListResponseDto.getUpcomingChallenges();
    }

    @GetMapping("/ongoing")
    public List<ChallengeListResponseDto> getOngoingChallenge() {
        MyChallengeListResponseDto myChallengeListResponseDto = myPageService.getMyChallenges(userEmail);
        return myChallengeListResponseDto.getOngoingChallenges();
    }

    @GetMapping("/finished")
    public List<ChallengeListResponseDto> getFinishedChallenge() {
        MyChallengeListResponseDto myChallengeListResponseDto = myPageService.getMyChallenges(userEmail);
        return myChallengeListResponseDto.getFinishedChallenges();
    }

    @GetMapping("/reviews")
    public List<ReviewListResponseDto> getMyReviews(@AuthenticationPrincipal UserDetails userDetails) {
        String jwtToken = ((JwtAuthenticationToken) userDetails).getToken().getTokenValue();
        return myPageService.getUserReviews(jwtToken);
    }

    @GetMapping("/missions")
    public List<MyMissionResponseDto> getUserMissions(@AuthenticationPrincipal JwtAuthenticationToken jwtAuthenticationToken) {
        String jwtToken = jwtAuthenticationToken.getToken().getTokenValue();
        return myPageService.getUserMissions(jwtToken);
    }
}
