package com.wink.inssa_backend.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {
    private String userId; // String 타입의 userId
    private String nickname;
    private String password;
    private String role; // 역할 필드 추가

}