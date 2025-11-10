package com.codeplay.codeplay_api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "stage")
@Data
public class Stage {

    @Id
    @Column(name = "id_stage", length = 50)
    private String idStage;

    @Column(name = "nama_stage", length = 100)
    private String namaStage;

    private String deskripsi;

    @Enumerated(EnumType.STRING)
    private StageType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lesson")
    private Lesson lesson;

    @Column(name = "id_materi", length = 50)
    private String idMateri;

    @Column(name = "id_question", length = 50)
    private String idQuestion;

    public enum StageType {
        materi,
        quiz,
    }
}
