package com.codeplay.codeplay_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Table(name = "users")
@Data

public class User {
    @Id
    @Column(name = "id_user", length = 50)
    private String idUser;

    private String nama;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(name = "no_hp", length = 15)
    private String noHp;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "current_energy")
    private int currentEnergy;

    @Column(name = "max_energy")
    private int maxEnergy;

    @Column(name = "last_energy_update")
    private LocalDateTime lastEnergyUpdate;

    @Column(name = "is_premium")
    private Boolean isPremium;

    public enum Role {
        admin, user
    }

    public User(String idUser) {
        this.idUser = idUser;
    }

    public User() {
        //TODO Auto-generated constructor stub
    }
}
