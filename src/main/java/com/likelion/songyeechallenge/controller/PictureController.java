package com.likelion.songyeechallenge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Paths;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class PictureController {

    private static final String imagePath = "src/main/resources/static/images/";

    @GetMapping("/picture")
    public ResponseEntity<Resource> returnImage(@RequestParam String pictureName) {
        String fullPath = Paths.get(imagePath, pictureName).toString();
        Resource resource = new FileSystemResource(fullPath);

        // Content-Type 설정
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + pictureName + "\"")
                .header("Content-Type", "image/png") // 이미지 타입에 맞게 설정
                .body(resource);
    }
}
