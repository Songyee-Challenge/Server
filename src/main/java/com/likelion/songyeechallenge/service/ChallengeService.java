package com.likelion.songyeechallenge.service;

import com.likelion.songyeechallenge.config.JwtTokenProvider;
import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.domain.challenge.ChallengeRepository;
import com.likelion.songyeechallenge.domain.mission.Mission;
import com.likelion.songyeechallenge.domain.mission.MissionRepository;
import com.likelion.songyeechallenge.domain.picture.Picture;
import com.likelion.songyeechallenge.domain.user.User;
import com.likelion.songyeechallenge.domain.user.UserRepository;
import com.likelion.songyeechallenge.web.dto.ChallengeDetailResponseDto;
import com.likelion.songyeechallenge.web.dto.ChallengeListResponseDto;
import com.likelion.songyeechallenge.web.dto.ChallengeSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChallengeService {

    private LocalDate today = LocalDate.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    String formattedToday = today.format(formatter);

    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final MissionRepository missionRepository;
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

        User author = userRepository.findByUser_id(jwtTokenProvider.getUserIdFromToken(jwtToken));
        challenge.getParticipants().add(author);

        challengeRepository.save(challenge);

        List<Mission> missionsForChallenge = missionRepository.findByChallengeId(challenge.getChallenge_id());
        for (Mission mission : missionsForChallenge) {
            mission.setUser(author);
            missionRepository.save(mission);
        }

        return challenge;
    }

    @Transactional(readOnly = true)
    public List<ChallengeListResponseDto> findRecruitingPost() {
        return challengeRepository.findBeforeStartDesc(formattedToday).stream()
                .map(challenge -> new ChallengeListResponseDto(challenge, pictureService))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ChallengeListResponseDto> findInProcessPost() {
        return challengeRepository.findInProcessDesc(formattedToday).stream()
                .map(challenge -> new ChallengeListResponseDto(challenge, pictureService))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ChallengeListResponseDto> findFinishedPost() {
        return challengeRepository.findFinishedDesc(formattedToday).stream()
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

    @Transactional
    public Long joinChallenge(Long challengeId, String jwtToken) {
        User participant = userRepository.findByUser_id(jwtTokenProvider.getUserIdFromToken(jwtToken));
        Challenge challenge = challengeRepository.findByChallenge_id(challengeId);
        challenge.getParticipants().add(participant);
        challengeRepository.save(challenge);

        List<Mission> missionsForChallenge = missionRepository.findByChallengeId(challenge.getChallenge_id());
        for (Mission mission : missionsForChallenge) {
            mission.setUser(participant);
            missionRepository.save(mission);
        }

        return participant.getUser_id();
    }
}
