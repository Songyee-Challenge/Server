package com.likelion.songyeechallenge.service;

import com.likelion.songyeechallenge.config.JwtTokenProvider;
import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.domain.challenge.ChallengeRepository;
import com.likelion.songyeechallenge.domain.mission.Mission;
import com.likelion.songyeechallenge.domain.mission.MissionRepository;
import com.likelion.songyeechallenge.domain.userMission.UserMission;
import com.likelion.songyeechallenge.domain.userMission.UserMissionRepository;
import com.likelion.songyeechallenge.domain.review.Review;
import com.likelion.songyeechallenge.domain.review.ReviewRepository;
import com.likelion.songyeechallenge.domain.user.User;
import com.likelion.songyeechallenge.domain.user.UserRepository;
import com.likelion.songyeechallenge.web.dto.ChallengeListResponseDto;
import com.likelion.songyeechallenge.web.dto.MyMissionResponseDto;
import com.likelion.songyeechallenge.web.dto.MyReviewResponseDto;
import com.likelion.songyeechallenge.web.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyPageService {

    private LocalDate today = LocalDate.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    String formattedToday = today.format(formatter);

    private final ChallengeRepository challengeRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ReviewRepository reviewRepository;
    private final UserMissionRepository userMissionRepository;
    private final MissionRepository missionRepository;
    private final UserRepository userRepository;

    @Transactional
    public String findMyName(String jwtToken) {
        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        User user = userRepository.findByUser_id(userId);
        return user.getName();
    }

    @Transactional(readOnly = true)
    public List<ChallengeListResponseDto> findMyRecruiting(String jwtToken) {
        return findChallengesByStatus(jwtToken, today -> challengeRepository.findBeforeStartDesc(formattedToday));
    }

    @Transactional(readOnly = true)
    public List<ChallengeListResponseDto> findMyInProcess(String jwtToken) {
        return findChallengesByStatus(jwtToken, today -> challengeRepository.findInProcessDesc(formattedToday));
    }

    @Transactional(readOnly = true)
    public List<ChallengeListResponseDto> findMyFinished(String jwtToken) {
        return findChallengesByStatus(jwtToken, today -> challengeRepository.findFinishedDesc(formattedToday));
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
                .map(challenge -> {
                    List<Mission> missions = missionRepository.findByChallengeId(challenge.getChallenge_id());
                    List<UserMission> userMissions = missions.stream()
                            .map(mission -> userMissionRepository.findMyMission(userId, mission.getMission_id()))
                            .collect(Collectors.toList());

                    return new MyMissionResponseDto(challenge, userMissions);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public UserInfoDto findMyInfo(String jwtToken) {
        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        User user = userRepository.findByUser_id(userId);
        UserInfoDto userInfoDto = convertToUserInfoDto(user);
        return userInfoDto;
    }

    @Transactional
    public void deleteAccount(String jwtToken) {
        try {
            Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
            User user = userRepository.findByUser_id(userId);
            userRepository.delete(user);
        } catch (Exception e) {
            // 실패 시 예외를 던져서 실패를 나타냄
            throw new RuntimeException("Failed to delete user account.");
        }
    }

    @Transactional
    public boolean isCompleteMission(Long missionId, String jwtToken) {
        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        UserMission userMission = userMissionRepository.findMyMission(userId, missionId);
        userMission.setComplete(!userMission.isComplete());
        userMissionRepository.save(userMission);
        return userMission.isComplete();
    }

    private List<ChallengeListResponseDto> findChallengesByStatus(String jwtToken, Function<Long, List<Challenge>> challengeFinder) {
        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        Set<Challenge> participatedChallenges = challengeRepository.findByParticipants(userId);

        return challengeFinder.apply(userId).stream()
                .filter(participatedChallenges::contains)
                .map(ChallengeListResponseDto::new)
                .collect(Collectors.toList());
    }

    private UserInfoDto convertToUserInfoDto(User user) {
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setUser_id(user.getUser_id());
        userInfoDto.setName(user.getName());
        userInfoDto.setEmail(user.getEmail());
        userInfoDto.setMajor(user.getMajor());
        userInfoDto.setStudent_id(user.getStudent_id());
        return userInfoDto;
    }
}
