package com.likelion.songyeechallenge.web.dto;

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
    public PictureResponseDto(String fileName, String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }
}
