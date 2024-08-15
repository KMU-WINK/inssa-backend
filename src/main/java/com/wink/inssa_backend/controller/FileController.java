package com.wink.inssa_backend.controller;

import com.wink.inssa_backend.domain.ImageFile;
import com.wink.inssa_backend.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    // 파일 업로드 API
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // 파일 URL 생성 로직을 FileService에 위임
            String fileUrl = fileService.storeFile(file);

            // 파일 정보 저장
            ImageFile imageFile = ImageFile.builder()
                    .name(file.getOriginalFilename())
                    .url(fileUrl)
                    .uploadedAt(LocalDateTime.now())
                    .build();

            fileService.saveFile(imageFile);

            return ResponseEntity.status(HttpStatus.CREATED).body("File uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file!");
        }
    }

    // 파일 다운로드 API
    @GetMapping("/download/{id}")
    public ResponseEntity<String> downloadFile(@PathVariable Long id) {
        ImageFile imageFile = fileService.getFile(id);

        if (imageFile == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
        }

        return ResponseEntity.ok(imageFile.getUrl());
    }
}