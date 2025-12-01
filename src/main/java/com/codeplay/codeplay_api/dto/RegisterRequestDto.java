package com.codeplay.codeplay_api.dto;

import lombok.Data;

@Data

public class RegisterRequestDto {
    private String nama;
    private String email;
    private String password;
    private String noHp;
}