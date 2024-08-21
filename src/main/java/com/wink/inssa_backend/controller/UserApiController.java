package com.wink.inssa_backend.controller;

import com.wink.inssa_backend.dto.LoginRequestDTO;
import com.wink.inssa_backend.service.AddUserRequest;
import com.wink.inssa_backend.service.UserService;
import com.wink.inssa_backend.dto.LoginRequestDTO;
import com.wink.inssa_backend.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final AuthService authService; // 새로운 AuthService 추가

    // 회원 가입 처리
    @CrossOrigin(origins = "http://localhost:8080") // 허용할 출처를 지정합니다.
    @PostMapping("/api/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody AddUserRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        userService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    // 로그인 처리
    @PostMapping("/api/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO request) {
        String token = authService.authenticate(request.getUserId(), request.getPassword());

        if (token != null) {
            return ResponseEntity.ok().body("Login successful. Token: " + token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    // 로그아웃 처리
    @GetMapping("/api/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());

        return ResponseEntity.ok("User logged out successfully");
    }
}