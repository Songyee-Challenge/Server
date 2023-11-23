package com.likelion.songyeechallenge.config.dto;
import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.service.PictureService;
import com.likelion.songyeechallenge.web.dto.ChallengeListResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MyChallengeListResponseDto {
    private List<ChallengeListResponseDto> upcomingChallenges;
    private List<ChallengeListResponseDto> ongoingChallenges;
    private List<ChallengeListResponseDto> finishedChallenges;

    public MyChallengeListResponseDto(List<ChallengeListResponseDto> upcomingChallenges, List<ChallengeListResponseDto> ongoingChallenges, List<ChallengeListResponseDto> finishedChallenges) {
        this.upcomingChallenges = upcomingChallenges;
        this.ongoingChallenges = ongoingChallenges;
        this.finishedChallenges = finishedChallenges;
    }
}

