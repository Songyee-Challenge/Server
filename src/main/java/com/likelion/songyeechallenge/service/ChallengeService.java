package com.likelion.songyeechallenge.service;

import com.likelion.songyeechallenge.config.JwtTokenProvider;
import com.likelion.songyeechallenge.config.dto.MyChallengeListResponseDto;
import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.domain.challenge.ChallengeRepository;
import com.likelion.songyeechallenge.domain.mission.Mission;
import com.likelion.songyeechallenge.domain.picture.Picture;
import com.likelion.songyeechallenge.web.dto.ChallengeDetailResponseDto;
import com.likelion.songyeechallenge.web.dto.ChallengeListResponseDto;
import com.likelion.songyeechallenge.web.dto.ChallengeSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChallengeService {

    private final ChallengeService challengeService;
    private LocalDate today = LocalDate.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    String formatedToday = today.format(formatter);

    private final ChallengeRepository challengeRepository;
    private final PictureService pictureService;
    private final MissionService missionService;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Challenge postChallenge(ChallengeSaveRequestDto requestDto, MultipartFile file, String jwtToken) {
        Challenge challenge = challengeRepository.save(requestDto.toEntity());

        String writer = jwtTokenProvider.getUserMajorFromToken(jwtToken) + " " + jwtTokenProvider.getUserNameFromToken(jwtToken);
        challenge.setWriter(writer);

        Picture picture = pictureService.uploadPicture(file);
        picture.setChallenge(challenge);

        List<Mission> missions = missionService.uploadMission(requestDto.getMissions(), challenge);
        challenge.setMissions(missions);

        challengeRepository.save(challenge);
        return challenge;
    }

    @Transactional(readOnly = true)
    public List<ChallengeListResponseDto> findRecruitingPost() {
        return challengeRepository.findBeforeStartDateDesc(formatedToday).stream()
                .map(challenge -> new ChallengeListResponseDto(challenge, pictureService))
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<ChallengeListResponseDto> findInProcessPost() {
        return challengeRepository.findBetweenStartDateAndEndDateDesc(formatedToday).stream()
                .map(challenge -> new ChallengeListResponseDto(challenge, pictureService))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ChallengeListResponseDto> findFinishedPost() {
        return challengeRepository.findAfterEndDateDesc(formatedToday).stream()
                .map(challenge -> new ChallengeListResponseDto(challenge, pictureService))
                .collect(Collectors.toList());
    }

    public ChallengeDetailResponseDto findById(Long challenge_id) {
        Challenge challenge = challengeRepository.findById(challenge_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + challenge_id));
        return new ChallengeDetailResponseDto(challenge, pictureService);
    }

    @Transactional(readOnly = true)
    public List<ChallengeListResponseDto> search(String searchWord) {
        return challengeRepository.findByTitleOrCategory(searchWord).stream()
                .map(challenge -> new ChallengeListResponseDto(challenge, pictureService))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ChallengeListResponseDto> findUpcomingChallenges(String userEmail, String formattedToday) {
        // 사용자가 개설했거나 개설 신청한 아직 시작되지 않은 챌린지 가져오기
        List<ChallengeListResponseDto> userCreatedUpcomingChallenges = challengeRepository.findUpcomingChallengesByCreator(userEmail, formattedToday)
                .stream()
                .map(challenge -> new ChallengeListResponseDto(challenge, pictureService))
                .collect(Collectors.toList());

        // 사용자가 참가한 아직 시작되지 않은 챌린지 가져오기
        List<ChallengeListResponseDto> userParticipatedUpcomingChallenges = challengeRepository.findUpcomingChallengesByParticipant(userEmail, formattedToday)
                .stream()
                .map(challenge -> new ChallengeListResponseDto(challenge, pictureService))
                .collect(Collectors.toList());

        // 중복 제거 후 합치기
        Set<Long> uniqueChallengeIds = new HashSet<>();
        uniqueChallengeIds.addAll(
                userCreatedUpcomingChallenges.stream()
                        .map(challenge -> challenge.getChallengeId())
                        .collect(Collectors.toList()));
        uniqueChallengeIds.addAll(
                userParticipatedUpcomingChallenges.stream()
                        .map(challenge -> challenge.getChallengeId())
                        .collect(Collectors.toList()));

        List<ChallengeListResponseDto> allUpcomingChallenges = uniqueChallengeIds.stream()
                .map(challengeId -> challengeRepository.findById(challengeId).orElse(null))
                .filter(Objects::nonNull)
                .map(challenge -> new ChallengeListResponseDto(challenge, pictureService))
                .collect(Collectors.toList());


        return allUpcomingChallenges;
    }

    @Transactional(readOnly = true)
    public List<ChallengeListResponseDto> findOngoingChallenges(String userEmail) {
        // 진행 중인 챌린지 가져오기
        List<ChallengeListResponseDto> ongoingChallenges = challengeRepository.findOngoingChallenges(userEmail, formatedToday)
                .stream()
                .map(challenge -> new ChallengeListResponseDto(challenge, pictureService))
                .collect(Collectors.toList());

        return ongoingChallenges;
    }

    @Transactional(readOnly = true)
    public List<ChallengeListResponseDto> findFinishedChallenges(String userEmail, String formattedToday) {
        // 종료된 챌린지 가져오기
        List<ChallengeListResponseDto> finishedChallenges = challengeRepository.findFinishedChallenges(userEmail, formattedToday)
                .stream()
                .map(challenge -> new ChallengeListResponseDto(challenge, pictureService))
                .collect(Collectors.toList());

        return finishedChallenges;
    }


}
