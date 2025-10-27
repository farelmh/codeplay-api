package com.codeplay.codeplay_api.entity;

import jakarta.persistence.Entity;
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
    private String email;
    private String password;

    @Column(name = "no_hp", length = 15)
    private String noHp;

    private String role;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
