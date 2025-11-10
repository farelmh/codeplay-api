package com.codeplay.codeplay_api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Entity
@Table(name = "materi")
@Data
public class Materi {

    @Id
    @Column(name = "id_materi", length = 50)
    private String idMateri;

    @Column(name = "konten", columnDefinition = "LONGTEXT")
    private String konten;

    @Column(name = "file_url", length = 255)
    private String fileUrl;

    @Column(name = " created_at")
    private LocalDateTime cratedAt;
}
