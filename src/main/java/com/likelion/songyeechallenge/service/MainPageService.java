package com.likelion.songyeechallenge.service;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.domain.challenge.ChallengeRepository;
import com.likelion.songyeechallenge.web.dto.ChallengeDetailResponseDto;
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
    private String formattedToday = today.format(formatter);

    private final ChallengeRepository challengeRepository;

    public List<MainPageResponseDto> findImminentPost() {
        List<Challenge> challenges = challengeRepository.findImminent(formattedToday);
        return findPosts(challenges, 4);
    }

    public List<MainPageResponseDto> findImminentPostAll() {
        List<Challenge> challenges = challengeRepository.findImminent(formattedToday);
        return findPosts(challenges, Integer.MAX_VALUE);
    }

    public List<MainPageResponseDto> findHotBeforeStartPost() {
        List<Challenge> challenges = challengeRepository.findBeforeStartHot(formattedToday);
        return findPosts(challenges, 8);
    }

    public List<MainPageResponseDto> findHotBeforeStartPostAll() {
        List<Challenge> challenges = challengeRepository.findBeforeStartHot(formattedToday);
        return findPosts(challenges, Integer.MAX_VALUE);
    }

    public List<ChallengeListResponseDto> findByCategory(String category) {
        List<Challenge> challenges = challengeRepository.findByCategory(category);
        return challenges.stream()
                .map(ChallengeListResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<MainPageResponseDto> findPosts(List<Challenge> challenges, int limit) {
        return challenges.stream()
                .limit(limit)
                .map(MainPageResponseDto::new)
                .collect(Collectors.toList());
    }
}
