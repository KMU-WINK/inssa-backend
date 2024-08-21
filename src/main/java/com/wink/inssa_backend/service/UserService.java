package com.wink.inssa_backend.service;

import com.wink.inssa_backend.domain.User;
import com.wink.inssa_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public Long save(AddUserRequest dto) {
        // userId 중복 검사
        if (userRepository.findByUserId(dto.getUserId()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자 ID입니다.");
        }

        // 사용자 저장
        String role = dto.getRole() != null ? dto.getRole() : "USER"; // role 기본값 설정

        User user = User.builder()
                .userId(dto.getUserId()) // userId 사용
                .password(bCryptPasswordEncoder.encode(dto.getPassword())) // 패스워드 암호화
                .nickname(dto.getNickname())
                .role(role) // role 설정
                .build();

        return userRepository.save(user).getId();
    }

    @Transactional(readOnly = true)
    public User findByUserId(String userId) {
        // userId로 사용자 찾기
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자 ID로 사용자를 찾을 수 없습니다. ID: " + userId));
    }

    @Transactional
    public void deleteByUserId(String userId) {
        // userId로 사용자 삭제
        User user = findByUserId(userId);
        userRepository.delete(user);
    }
}