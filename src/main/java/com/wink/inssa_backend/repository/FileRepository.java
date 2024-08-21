package com.wink.inssa_backend.repository;

import com.wink.inssa_backend.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}