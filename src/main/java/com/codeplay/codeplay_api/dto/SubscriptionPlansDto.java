package com.codeplay.codeplay_api.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SubscriptionPlansDto {
    private String idPlan;
    private String nama;
    private String deskripsi;
    private int durasiBulan;
    private BigDecimal harga;
}