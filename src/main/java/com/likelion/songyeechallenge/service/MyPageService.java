package com.likelion.songyeechallenge.service;

import com.likelion.songyeechallenge.config.JwtTokenProvider;
import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.domain.challenge.ChallengeRepository;
import com.likelion.songyeechallenge.domain.like.Like;
import com.likelion.songyeechallenge.domain.mission.Mission;
import com.likelion.songyeechallenge.domain.mission.MissionRepository;
import com.likelion.songyeechallenge.domain.userMission.UserMission;
import com.likelion.songyeechallenge.domain.userMission.UserMissionRepository;
import com.likelion.songyeechallenge.domain.review.Review;
import com.likelion.songyeechallenge.domain.review.ReviewRepository;
import com.likelion.songyeechallenge.domain.user.User;
import com.likelion.songyeechallenge.domain.user.UserRepository;
import com.likelion.songyeechallenge.web.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyPageService {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

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
    public List<MyChallengeListResponseDto> findMyRecruitingTop2(String jwtToken) {
        LocalDate today = LocalDate.now();
        String formattedToday = today.format(formatter);

        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        Set<Challenge> participatedChallenges = challengeRepository.findByParticipants(userId);

        List<Challenge> myImminentChallenges = challengeRepository.findImminent(formattedToday).stream()
                .filter(participatedChallenges::contains)
                .collect(Collectors.toList());
        int myChallengeNumber = (int) myImminentChallenges.size();

        return myImminentChallenges.stream()
                .limit(2)
                .map(myChallenge -> new MyChallengeListResponseDto(myChallenge, myChallengeNumber))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MyChallengeListResponseDto> findMyInProcessTop2(String jwtToken) {
        LocalDate today = LocalDate.now();
        String formattedToday = today.format(formatter);

        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        Set<Challenge> participatedChallenges = challengeRepository.findByParticipants(userId);

        List<Challenge> myInProcessEndingSoons = challengeRepository.findInProcessEndingSoon(formattedToday).stream()
                .filter(participatedChallenges::contains)
                .collect(Collectors.toList());
        int myChallengeNumber = (int) myInProcessEndingSoons.size();

        return myInProcessEndingSoons.stream()
                .limit(2)
                .map(myChallenge -> new MyChallengeListResponseDto(myChallenge, myChallengeNumber))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MyChallengeListResponseDto> findMyFinishedTop2(String jwtToken) {
        LocalDate today = LocalDate.now();
        String formattedToday = today.format(formatter);

        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        Set<Challenge> participatedChallenges = challengeRepository.findByParticipants(userId);

        List<Challenge> myJustFinished = challengeRepository.findJustFinished(formattedToday).stream()
                .filter(participatedChallenges::contains)
                .collect(Collectors.toList());
        int myChallengeNumber = (int) myJustFinished.size();

        return myJustFinished.stream()
                .limit(2)
                .map(myChallenge -> new MyChallengeListResponseDto(myChallenge, myChallengeNumber))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ChallengeListResponseDto> findMyRecruiting(String jwtToken) {
        LocalDate today = LocalDate.now();
        String formattedToday = today.format(formatter);

        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        Set<Challenge> participatedChallenges = challengeRepository.findByParticipants(userId);

        return challengeRepository.findBeforeStartDesc(formattedToday).stream()
                .filter(participatedChallenges::contains)
                .map(ChallengeListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ChallengeListResponseDto> findMyInProcess(String jwtToken) {
        LocalDate today = LocalDate.now();
        String formattedToday = today.format(formatter);

        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        Set<Challenge> participatedChallenges = challengeRepository.findByParticipants(userId);

        return challengeRepository.findInProcessDesc(formattedToday).stream()
                .filter(participatedChallenges::contains)
                .map(ChallengeListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ChallengeListResponseDto> findMyFinished(String jwtToken) {
        LocalDate today = LocalDate.now();
        String formattedToday = today.format(formatter);

        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        Set<Challenge> participatedChallenges = challengeRepository.findByParticipants(userId);

        return challengeRepository.findFinishedDesc(formattedToday).stream()
                .filter(participatedChallenges::contains)
                .map(ChallengeListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReviewListResponseDto> findMyReview(String jwtToken) {
        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        User user = userRepository.findByUser_id(userId);
        List<Long> likedReviewIdList = user.getLikes().stream()
                .map(Like::getReview)
                .map(Review::getReview_id)
                .collect(Collectors.toList());

        List<Review> reviews = reviewRepository.findByUser(userId);

        return reviews.stream()
                .map(review -> new ReviewListResponseDto(review, likedReviewIdList.contains(review.getReview_id())))
                .collect(Collectors.toList());
    }

    @Transactional
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
    public boolean isCompleteMission(Long missionId, String jwtToken) {
        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        UserMission userMission = userMissionRepository.findMyMission(userId, missionId);
        userMission.setComplete(!userMission.isComplete());
        userMissionRepository.save(userMission);
        return userMission.isComplete();
    }

    @Transactional
    public MyInfoDto findMyInfo(String jwtToken) {
        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        User user = userRepository.findByUser_id(userId);
        MyInfoDto myInfoDto = convertToUserInfoDto(user);
        return myInfoDto;
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

    private MyInfoDto convertToUserInfoDto(User user) {
        MyInfoDto myInfoDto = new MyInfoDto();
        myInfoDto.setUser_id(user.getUser_id());
        myInfoDto.setName(user.getName());
        myInfoDto.setEmail(user.getEmail());
        myInfoDto.setMajor(user.getMajor());
        myInfoDto.setStudent_id(user.getStudent_id());
        return myInfoDto;
    }
}
