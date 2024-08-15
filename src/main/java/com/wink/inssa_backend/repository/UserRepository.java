package com.wink.inssa_backend.repository;

import com.wink.inssa_backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId); // userId로 사용자 조회
}