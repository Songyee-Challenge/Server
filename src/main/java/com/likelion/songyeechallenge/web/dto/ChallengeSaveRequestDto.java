package com.likelion.songyeechallenge.web.dto;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.domain.mission.Mission;
import com.likelion.songyeechallenge.domain.picture.Picture;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ChallengeSaveRequestDto {
    private String title;
    private String writer;
    private String startDate;
    private String endDate;
    private String category;
    private String explain;
    private List<MissionSaveRequestDto> missions;
    private Picture picture;

    @Builder
    public ChallengeSaveRequestDto(String title, String writer, String startDate, String endDate, String category, String explain, List<MissionSaveRequestDto> missions, Picture picture) {
        this.title = title;
        this.writer = writer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.explain = explain;
        this.missions = missions;
        this.picture = picture;
    }

    public Challenge toEntity() {
        return Challenge.builder()
                .title(title)
                .writer(writer)
                .startDate(startDate)
                .endDate(endDate)
                .category(category)
                .explain(explain)
                .picture(picture)
                .build();
    }
}
