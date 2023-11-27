package com.likelion.songyeechallenge.controller;

import com.likelion.songyeechallenge.service.MainPageService;
import com.likelion.songyeechallenge.web.dto.ChallengeDetailResponseDto;
import com.likelion.songyeechallenge.web.dto.ChallengeListResponseDto;
import com.likelion.songyeechallenge.web.dto.MainPageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/main")
@RestController
public class MainPageController {

    private final MainPageService mainPageService;

    @GetMapping("/imminent")
    public List<MainPageResponseDto> getImminentChallenge() {
        return mainPageService.findImminentPost();
    }

    @GetMapping("/imminent/all")
    public List<MainPageResponseDto> getImminentChallengeAll() {
        return mainPageService.findImminentPostAll();
    }

    @GetMapping("/hot")
    public List<MainPageResponseDto> getHotInProcessChallenge() {
        return mainPageService.findHotBeforeStartPost();
    }

    @GetMapping("/hot/all")
    public List<MainPageResponseDto> getHotInProcessChallengeAll() {
        return mainPageService.findHotBeforeStartPostAll();
    }

    @GetMapping("/category")
    public List<ChallengeListResponseDto> getChallengeByCategory(@RequestParam String category) {
        return mainPageService.findByCategory(category);
    }
}
