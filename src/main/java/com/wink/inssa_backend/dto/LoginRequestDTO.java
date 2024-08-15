package com.wink.inssa_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDTO {

    @NotBlank(message = "아이디는 필수 항목입니다.")
    private String id;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    private String password;

    public LoginRequestDTO(String id, String password) {
        this.id = id;
        this.password = password;
    }
}