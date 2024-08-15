package com.wink.inssa_backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponseDTO {

    private String id;
    private String nickname;
    private String token;

    public LoginResponseDTO(String id, String nickname, String token) {
        this.id = id;
        this.nickname = nickname;
        this.token = token;
    }
}