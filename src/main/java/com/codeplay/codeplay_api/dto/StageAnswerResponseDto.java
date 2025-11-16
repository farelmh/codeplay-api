package com.codeplay.codeplay_api.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StageAnswerResponseDto {
    private boolean correct;
    private BigDecimal scoreEarned;
    private String message;
}

