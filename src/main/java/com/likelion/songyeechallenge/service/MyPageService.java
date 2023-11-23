package com.likelion.songyeechallenge.service;

import com.likelion.songyeechallenge.config.JwtTokenProvider;
import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.domain.challenge.ChallengeRepository;
import com.likelion.songyeechallenge.domain.mission.Mission;
import com.likelion.songyeechallenge.domain.mission.MissionRepository;
import com.likelion.songyeechallenge.domain.review.Review;
import com.likelion.songyeechallenge.domain.review.ReviewRepository;
import com.likelion.songyeechallenge.domain.user.UserRepository;
import com.likelion.songyeechallenge.web.dto.ChallengeListResponseDto;
import com.likelion.songyeechallenge.web.dto.MyMissionResponseDto;
import com.likelion.songyeechallenge.web.dto.MyReviewResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyPageService {

    private LocalDate today = LocalDate.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    String formattedToday = today.format(formatter);

    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PictureService pictureService;
    private final ReviewRepository reviewRepository;
    private final MissionRepository missionRepository;

    @Transactional(readOnly = true)
    public List<ChallengeListResponseDto> findMyRecruiting(String jwtToken) {
        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        Set<Challenge> participatedChallenges = challengeRepository.findByParticipants(userId);

        return challengeRepository.findBeforeStartDesc(formattedToday).stream()
                .filter(participatedChallenges::contains)
                .map(ChallengeListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ChallengeListResponseDto> findMyInProcess(String jwtToken) {
        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        Set<Challenge> participatedChallenges = challengeRepository.findByParticipants(userId);

        return challengeRepository.findInProcessDesc(formattedToday).stream()
                .filter(participatedChallenges::contains)
                .map(ChallengeListResponseDto::new)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<ChallengeListResponseDto> findMyFinished(String jwtToken) {
        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        Set<Challenge> participatedChallenges = challengeRepository.findByParticipants(userId);

        return challengeRepository.findFinishedDesc(formattedToday).stream()
                .filter(participatedChallenges::contains)
                .map(ChallengeListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MyReviewResponseDto> findMyReview(String jwtToken) {
        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        List<Review> reviews = reviewRepository.findByUser(userId);

        return reviews.stream()
                .map(MyReviewResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MyMissionResponseDto> findMyChallengeAndMission(String jwtToken) {
        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        Set<Challenge> participatedChallenges = challengeRepository.findByParticipants(userId);

        return participatedChallenges.stream()
                .map(MyMissionResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean isCompleteMission(Long missionId, Long challengeId, String jwtToken) {
        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);

        Mission mission = missionRepository.findMyMissionCompleteness(userId, missionId, challengeId);
        mission.setComplete(!mission.isComplete());
        missionRepository.save(mission);
        return mission.isComplete();
    }
}
