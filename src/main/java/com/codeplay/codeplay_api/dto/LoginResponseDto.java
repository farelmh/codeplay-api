package com.codeplay.codeplay_api.dto;

import lombok.Data;

@Data
public class LoginResponseDto {

    private String idUser, message, nama;

    public LoginResponseDto(String idUser, String message, String nama) {
        this.idUser = idUser;
        this.message = message;
        this.nama = nama;
    }
}
