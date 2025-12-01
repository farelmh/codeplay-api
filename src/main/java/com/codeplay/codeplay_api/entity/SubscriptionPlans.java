package com.codeplay.codeplay_api.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "subscription_plans")
@Data
public class SubscriptionPlans {
    @Id
    @Column(name = "id_plan", length = 50)
    private String idPlan;

    private String nama;
    private String deskripsi;
    
    @Column(name = "durasi_bulan")
    private int durasiBulan;

    private BigDecimal harga;
}
