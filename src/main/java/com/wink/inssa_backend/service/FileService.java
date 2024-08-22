package com.wink.inssa_backend.service;

import com.wink.inssa_backend.domain.File;
import com.wink.inssa_backend.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FileService {

    private final FileRepository fileRepository;

    public List<File> saveFiles(MultipartFile[] files) throws IOException {
        List<File> savedFiles = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();

            // 파일의 바이너리 데이터를 데이터베이스에 저장
            File savedFile = File.builder()
                    .name(fileName)
                    .data(file.getBytes())
                    .uploadedAt(LocalDateTime.now())
                    .build();

            savedFiles.add(fileRepository.save(savedFile));
        }

        return savedFiles;
    }

    public File getFile(Long id) {
        return fileRepository.findById(id).orElse(null);
    }
}