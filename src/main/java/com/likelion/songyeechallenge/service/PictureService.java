package com.likelion.songyeechallenge.service;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.domain.picture.Picture;
import com.likelion.songyeechallenge.domain.picture.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PictureService {

    private static String uploadDir = "./src/main/resources/static/images";
    private final PictureRepository pictureRepository;

    @Transactional
    public Picture uploadPicture(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        try {
            String originalName = file.getOriginalFilename();
            String newName = createFileUrl(originalName);

            Path uploadPath = Paths.get(uploadDir);
            Path filePath = uploadPath.resolve(newName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Picture picture = Picture.builder()
                    .originalName(originalName)
                    .newName(newName)
                    .build();

            pictureRepository.save(picture);
            return picture;
        } catch (IOException e) {
            throw new RuntimeException("사진 저장을 실패했습니다.", e);
        }
    }

    private String createFileUrl(String fileName) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(fileName);
        String newName = uuid + ext;

        return newName;
    }

    private String extractExt(String filename) {
        int idx = filename.lastIndexOf(".");
        String ext = filename.substring(idx);

        return ext;
    }

    public String getPictureUrl(Challenge challenge) {
        Picture picture = challenge.getPicture();
        return uploadDir + "/" + picture.getNewName();
    }
}
