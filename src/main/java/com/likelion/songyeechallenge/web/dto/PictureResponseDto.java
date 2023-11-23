package com.likelion.songyeechallenge.web.dto;

import com.likelion.songyeechallenge.domain.picture.Picture;
import com.likelion.songyeechallenge.service.PictureService;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PictureResponseDto {
    private Long picture_id;
    private String fileName;
    private String fileUrl;

    @Builder
    public PictureResponseDto(Picture fileName, PictureService fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }
}
