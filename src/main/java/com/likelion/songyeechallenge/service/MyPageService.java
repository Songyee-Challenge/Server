package com.likelion.songyeechallenge.service;

import com.likelion.songyeechallenge.config.JwtTokenProvider;
import com.likelion.songyeechallenge.config.dto.MyChallengeListResponseDto;
import com.likelion.songyeechallenge.web.dto.ChallengeListResponseDto;
import com.likelion.songyeechallenge.web.dto.ReviewListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MyPageService {

    private final ChallengeService challengeService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ReviewService reviewService;

    @Transactional(readOnly = true)
    public MyChallengeListResponseDto getMyChallenges(String jwtToken) {
        String userEmail = jwtTokenProvider.getUserEmailFromToken(jwtToken);
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedToday = today.format(formatter);

        List<ChallengeListResponseDto> upcomingChallenges = challengeService.findUpcomingChallenges(userEmail, formattedToday);
        List<ChallengeListResponseDto> ongoingChallenges = challengeService.findOngoingChallenges(userEmail);
        List<ChallengeListResponseDto> finishedChallenges = challengeService.findFinishedChallenges(userEmail, formattedToday);

        return new MyChallengeListResponseDto(upcomingChallenges, ongoingChallenges, finishedChallenges);
    }

    @Transactional(readOnly = true)
    public List<ReviewListResponseDto> getUserReviews(String jwtToken) {
        String userEmail = jwtTokenProvider.getUserEmailFromToken(jwtToken);
        return reviewService.findUserReviews(userEmail);
    }

    @Transactional(readOnly = true)
    public List<MyMissionResponseDto> getUserMissions(String jwtToken) {
        String userEmail = jwtTokenProvider.getUserEmailFromToken(jwtToken);

        List<Mission> userMissions = missionRepository.findByChallengeParticipant(userEmail);

        return userMissions.stream()
                .map(mission -> new MyMissionResponseDto(mission, pictureService))
                .collect(Collectors.toList());
    }
}
