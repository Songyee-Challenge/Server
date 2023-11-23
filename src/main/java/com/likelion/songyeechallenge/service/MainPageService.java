package com.likelion.songyeechallenge.service;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.domain.challenge.ChallengeRepository;
import com.likelion.songyeechallenge.web.dto.ChallengeListResponseDto;
import com.likelion.songyeechallenge.web.dto.MainPageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MainPageService {

    private LocalDate today = LocalDate.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private String formatedToday = today.format(formatter);

    private final ChallengeRepository challengeRepository;

    public List<MainPageResponseDto> findResentRecruitPost() {
        List<Challenge> challenges = challengeRepository.findBeforeStartDesc(formatedToday);
        return challenges.stream().limit(4)
                .map(MainPageResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<MainPageResponseDto> findHotInProcessPost() {
        List<Challenge> challenges = challengeRepository.findInProcessHot(formatedToday);
        return challenges.stream().limit(8)
                .map(MainPageResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ChallengeListResponseDto> findByCategory(String category) {
        List<Challenge> challenges = challengeRepository.findByCategory(category);
        return challenges.stream()
                .map(ChallengeListResponseDto::new)
                .collect(Collectors.toList());
    }
}
