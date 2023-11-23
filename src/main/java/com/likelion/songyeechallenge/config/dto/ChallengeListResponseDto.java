package com.likelion.songyeechallenge.config.dto;
import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.service.PictureService;
import com.likelion.songyeechallenge.web.dto.PictureResponseDto;
import lombok.Getter;

@Getter
public class ChallengeListResponseDto {

    private Long challengeId;
    private String title;
    private String startDate;
    private String endDate;
    private String category;
    private String writer;
    private PictureResponseDto picture;

    public ChallengeListResponseDto(Challenge challenge, PictureService pictureService) {
        this.challengeId = challenge.getChallengeId();
        this.title = challenge.getTitle();
        this.startDate = challenge.getStartDate();
        this.endDate = challenge.getEndDate();
        this.category = challenge.getCategory();
        this.writer = challenge.getWriter();
        this.picture = new PictureResponseDto(challenge.getPicture(), pictureService);
    }

    public Long getChallengeId() {
        return challengeId;
    }
}
