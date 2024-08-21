package com.wink.inssa_backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    // JwtTokenProvider와 UserDetailsService를 주입받는 생성자
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request); // 요청에서 토큰을 추출

        if (token != null && jwtTokenProvider.validateToken(token)) { // 토큰이 유효한 경우
            String userId = jwtTokenProvider.getUserIdFromToken(token); // 토큰에서 사용자 ID 추출
            UserDetails userDetails = userDetailsService.loadUserByUsername(userId); // 사용자 정보 로드

            if (userDetails != null) {
                // 인증 객체 생성 및 SecurityContext에 설정
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response); // 다음 필터로 요청 전달
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // 필터를 적용하지 않을 경로를 설정합니다.
        List<String> excludePaths = Arrays.asList("/api/login", "/api/signup", "/api/logout");

        String requestURI = request.getRequestURI();
        return excludePaths.stream().anyMatch(requestURI::startsWith);
    }
}