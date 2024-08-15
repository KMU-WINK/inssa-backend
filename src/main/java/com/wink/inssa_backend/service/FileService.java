package com.wink.inssa_backend.service;

import com.wink.inssa_backend.domain.ImageFile;
import com.wink.inssa_backend.repository.ImageFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Service
public class FileService {

    private final ImageFileRepository imageFileRepository;

    private final Path fileStorageLocation = Paths.get("file-storage").toAbsolutePath().normalize();

    // 파일을 저장하고 URL을 반환하는 메서드
    public String storeFile(MultipartFile file) throws IOException {
        // 파일 저장 로직
        Path targetLocation = this.fileStorageLocation.resolve(file.getOriginalFilename());
        Files.copy(file.getInputStream(), targetLocation);

        // 파일 접근 URL 반환 (여기서는 단순히 파일 이름을 URL로 사용)
        return targetLocation.toString();
    }

    // 파일 정보 저장
    public void saveFile(ImageFile imageFile) {
        imageFileRepository.save(imageFile);
    }

    // 파일 정보 가져오기
    public ImageFile getFile(Long id) {
        return imageFileRepository.findById(id).orElse(null);
    }
}