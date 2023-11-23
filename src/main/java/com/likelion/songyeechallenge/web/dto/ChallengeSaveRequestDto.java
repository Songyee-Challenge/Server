package com.likelion.songyeechallenge.web.dto;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.domain.picture.Picture;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

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
    public ChallengeSaveRequestDto(Challenge challenge) {
        this.title = challenge.getTitle();
        this.writer = challenge.getWriter();
        this.startDate = challenge.getStartDate();
        this.endDate = challenge.getEndDate();
        this.category = challenge.getCategory();
        this.explain = challenge.getExplain();
        this.picture = challenge.getPicture();
    }

    public Challenge toEntity() {
        return Challenge.builder()
                .title(title)
                .startDate(startDate)
                .endDate(endDate)
                .category(category)
                .explain(explain)
                .picture(picture)
                .build();
    }
}
