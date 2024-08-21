package com.wink.inssa_backend.service;

import com.wink.inssa_backend.domain.User;
import com.wink.inssa_backend.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final long EXPIRATION_TIME = 3600000; // 1시간 (밀리초 단위)

    // HS512에 적합한 강력한 키를 생성
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String authenticate(String userId, String password) {
        User user = userRepository.findByUserId(userId).orElse(null);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return generateToken(user);
        }

        return null; // 인증 실패 시 null 반환
    }

    private String generateToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getUserId());
        claims.put("role", user.getRole()); // 사용자 역할을 클레임에 추가

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey) // 생성된 SecretKey를 사용
                .compact();
    }
}