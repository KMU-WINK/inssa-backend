package com.wink.inssa_backend.service;

import com.wink.inssa_backend.domain.ImageFile;
import com.wink.inssa_backend.repository.ImageFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class FileService {

    private static final String FILE_DIRECTORY = "/path/to/your/directory"; // 실제 파일 저장 경로로 변경해야 합니다.

    @Autowired
    private ImageFileRepository imageFileRepository;

    public ImageFile saveFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(FILE_DIRECTORY, fileName);
        Files.write(filePath, file.getBytes());

        String fileUrl = filePath.toString();
        LocalDateTime uploadedAt = LocalDateTime.now();

        ImageFile imageFile = ImageFile.builder()
                .name(fileName)
                .url(fileUrl)
                .uploadedAt(uploadedAt)
                .build();

        return imageFileRepository.save(imageFile);
    }

    public ImageFile getFile(Long id) {
        return imageFileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));
    }
}