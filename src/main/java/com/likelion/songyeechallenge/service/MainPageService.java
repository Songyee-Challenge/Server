package com.likelion.songyeechallenge.service;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.domain.challenge.ChallengeRepository;
import com.likelion.songyeechallenge.web.dto.ChallengeListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MainPageService {

    private LocalDate today = LocalDate.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private String formattedToday = today.format(formatter);

    private final ChallengeRepository challengeRepository;

    public List<ChallengeListResponseDto> findImminentPost() {
        return findChallengesByStatus(() -> challengeRepository.findImminent(formattedToday));
    }

    public List<ChallengeListResponseDto> findHotInProcessPost() {
        return findChallengesByStatus(() -> challengeRepository.findBeforeStartHot(formattedToday));
    }

    public List<ChallengeListResponseDto> findByCategory(String category) {
        List<Challenge> challenges = challengeRepository.findByCategory(category);
        return challenges.stream()
                .map(ChallengeListResponseDto::new)
                .collect(Collectors.toList());
    }

    private List<ChallengeListResponseDto> findChallengesByStatus(Supplier<List<Challenge>> challengeList) {
        return challengeList.get().stream()
                .map(ChallengeListResponseDto::new)
                .collect(Collectors.toList());
    }
}
