package com.likelion.songyeechallenge.controller;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.service.ChallengeService;
import com.likelion.songyeechallenge.web.dto.ChallengeListResponseDto;
import com.likelion.songyeechallenge.web.dto.ChallengeSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/challenge")
@RestController
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping("/post")
    public Long postChallenge(@RequestPart(value = "dto") ChallengeSaveRequestDto saveRequestDto, @RequestParam("picture") MultipartFile file) {
        Challenge challenge = challengeService.postChallenge(saveRequestDto, file);
        return challenge.getChallenge_id();
    }

    @GetMapping("/recruit")
    public List<ChallengeListResponseDto> getRecruitChallenge() {
        return challengeService.findRecruitingPost();
    }

    @GetMapping("/inprocess")
    public List<ChallengeListResponseDto> getInProcessChallenge() {
        return challengeService.findInProcessPost();
    }

    @GetMapping("/finish")
    public List<ChallengeListResponseDto> getFinishChallenge() {
        return challengeService.findFinishedPost();
    }
}
