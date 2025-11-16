package com.codeplay.codeplay_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StageProgressResponseDto {

    private String idProgress;
    private String status;
    private String message;
}