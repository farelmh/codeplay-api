package com.codeplay.codeplay_api.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
    private String idUser, message;
    public LoginResponseDto(String idUser, String message) {
        this.idUser = idUser;
        this.message = message;
    }
}
