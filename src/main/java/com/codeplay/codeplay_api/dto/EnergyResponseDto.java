package com.codeplay.codeplay_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EnergyResponseDto {
    private boolean success;
    private String message;
    private Integer currentEnergy;
    private Integer maxEnergy;
    private Boolean isPremium;
}
