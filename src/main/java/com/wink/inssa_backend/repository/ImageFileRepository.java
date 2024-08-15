package com.wink.inssa_backend.repository;

import com.wink.inssa_backend.domain.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {
}