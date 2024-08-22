package com.wink.inssa_backend.controller;

import com.wink.inssa_backend.domain.File;
import com.wink.inssa_backend.service.FileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @PostMapping("/api/upload")
    public ResponseEntity<List<File>> uploadFile(@RequestParam("file") MultipartFile[] files) {
        logger.info("Received file upload request with {} files.", files.length);

        try {
            List<File> savedFiles = fileService.saveFiles(files);
            return ResponseEntity.ok(savedFiles);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/api/download/{id}")
    public ResponseEntity<String> downloadFile(@PathVariable("id") Long id) {
        File file = fileService.getFile(id);

        if (file == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
        }

        return ResponseEntity.ok(file.getName());
    }
}