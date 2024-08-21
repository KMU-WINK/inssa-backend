package com.wink.inssa_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequestDTO {

    @NotBlank(message = "id는 필수 항목입니다.")
    @Size(min = 3, max = 20, message = "아이디는 3자에서 20자 사이여야 합니다.")
    private String id;

    @NotBlank(message = "닉네임은 필수 항목입니다.")
    @Size(min = 3, max = 20, message = "닉네임은 3자에서 20자 사이여야 합니다.")
    private String nickname;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;

    // 기본 역할을 설정하기 위한 필드 추가
    @NotBlank(message = "역할은 필수 항목입니다.")
    private String role = "USER"; // 기본값을 "USER"로 설정

    public SignupRequestDTO(String id, String nickname, String password) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.role = "USER"; // 기본값을 "USER"로 설정
    }

    public SignupRequestDTO(String id, String nickname, String password, String role) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }
}