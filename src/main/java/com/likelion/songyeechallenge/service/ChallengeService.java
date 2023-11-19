package com.likelion.songyeechallenge.service;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.domain.challenge.ChallengeRepository;
import com.likelion.songyeechallenge.domain.mission.Mission;
import com.likelion.songyeechallenge.domain.picture.Picture;
import com.likelion.songyeechallenge.web.dto.ChallengeDetailResponseDto;
import com.likelion.songyeechallenge.web.dto.ChallengeListResponseDto;
import com.likelion.songyeechallenge.web.dto.ChallengeSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChallengeService {

    private LocalDate today = LocalDate.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    String formatedToday = today.format(formatter);

    private final ChallengeRepository challengeRepository;
    private final PictureService pictureService;
    private final MissionService missionService;

    @Transactional
    public Challenge postChallenge(ChallengeSaveRequestDto requestDto, MultipartFile file) {
        Challenge challenge = challengeRepository.save(requestDto.toEntity());
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
}
