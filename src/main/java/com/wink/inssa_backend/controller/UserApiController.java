package com.wink.inssa_backend.controller;

import com.wink.inssa_backend.service.AddUserRequest;
import com.wink.inssa_backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    // 회원 가입 처리
    @PostMapping("/api/user")
    public ResponseEntity<?> signup(@Valid @RequestBody AddUserRequest request, BindingResult result) {
        // 유효성 검사 실패 시 에러 응답 반환
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        // 유효성 검사 통과 시 회원 가입 처리
        userService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    // 로그아웃 처리
    @GetMapping("/api/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        // 로그아웃 처리 및 세션 무효화
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());

        // 로그아웃 후 성공 메시지 반환
        return ResponseEntity.ok("User logged out successfully");
    }
}