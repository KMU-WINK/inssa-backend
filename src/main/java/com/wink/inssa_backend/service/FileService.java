package com.wink.inssa_backend.service;

import com.wink.inssa_backend.domain.File;
import com.wink.inssa_backend.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FileService {

    private final FileRepository fileRepository;

    private final Path fileStorageLocation = Paths.get("file-storage").toAbsolutePath().normalize();

    // 파일을 저장하고 파일 엔티티 리스트를 반환하는 메서드
    public List<File> saveFiles(MultipartFile[] files) throws IOException {
        List<File> savedFiles = new ArrayList<>();

        // 파일 저장할 디렉토리가 없다면 생성
        Files.createDirectories(fileStorageLocation);

        for (MultipartFile file : files) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path targetLocation = this.fileStorageLocation.resolve(fileName);

            // 파일 저장
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // 파일 정보 저장
            File savedFile = File.builder()
                    .name(fileName)
                    .uploadedAt(LocalDateTime.now())
                    .build();

            savedFiles.add(fileRepository.save(savedFile));
        }

        return savedFiles;
    }

    // 파일 정보 가져오기
    public File getFile(Long id) {
        return fileRepository.findById(id).orElse(null);
    }

    // 파일 경로 반환
    public String getFilePath(File file) {
        return this.fileStorageLocation.resolve(file.getName()).toString();
    }
}