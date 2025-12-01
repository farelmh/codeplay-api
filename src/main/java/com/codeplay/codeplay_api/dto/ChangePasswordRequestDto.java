package com.codeplay.codeplay_api.dto;

import lombok.Data;

@Data
public class ChangePasswordRequestDto {
    private String email;
    private String newPassword;
}
