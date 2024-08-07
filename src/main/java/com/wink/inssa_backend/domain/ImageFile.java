package com.wink.inssa_backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "image_files")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ImageFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "uploaded_at", nullable = false)
    private LocalDateTime uploadedAt;

    @Builder
    public ImageFile(String name, String url, LocalDateTime uploadedAt) {
        this.name = name;
        this.url = url;
        this.uploadedAt = uploadedAt;
    }
}