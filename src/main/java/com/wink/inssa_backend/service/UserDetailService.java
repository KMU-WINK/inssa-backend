package com.wink.inssa_backend.service;

import lombok.RequiredArgsConstructor;
import com.wink.inssa_backend.domain.User;
import com.wink.inssa_backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
// 스프링 시큐리티에서 사용자 정보를 가져오는 인터페이스 구현체
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    // 사용자 이름(userId)으로 사용자 정보를 가져오는 메소드
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다. ID: " + userId));
    }
}