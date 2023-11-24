package com.likelion.songyeechallenge.controller;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.service.ChallengeService;
import com.likelion.songyeechallenge.web.dto.ChallengeDetailResponseDto;
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
    public Long postChallenge(@RequestPart(value = "dto") ChallengeSaveRequestDto saveRequestDto,
                              @RequestParam("picture") MultipartFile file,
                              @RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        Challenge challenge = challengeService.postChallenge(saveRequestDto, file, jwtToken);
        return challenge.getChallenge_id();
    }

    @GetMapping("/recruiting")
    public List<ChallengeListResponseDto> getRecruitChallenge() {
        return challengeService.findRecruitingPost();
    }

    @GetMapping("/inprocess")
    public List<ChallengeListResponseDto> getInProcessChallenge() {
        return challengeService.findInProcessPost();
    }

    @GetMapping("/finished")
    public List<ChallengeListResponseDto> getFinishChallenge() {
        return challengeService.findFinishedPost();
    }

    @GetMapping("/{id}")
    public ChallengeDetailResponseDto getChallengeDetail(@PathVariable Long id) {
        return challengeService.findById(id);
    }

    @GetMapping("/search")
    public List<ChallengeListResponseDto> searchChallenge(@RequestParam("searchWord") String searchWord) {
        return challengeService.search(searchWord);
    }

    @PostMapping("/register/{id}")
    public Long registerChallenge(@PathVariable("id") Long challengeId, @RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        Long participantId = challengeService.joinChallenge(challengeId, jwtToken);
        return participantId;
    }
}
