package com.codeplay.codeplay_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.data.annotation.Id;

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

    @Column(name = "id_materi")
    private String idMateri;

    @Column(name = "id_question")
    private String idQuestion;

    public enum StageType {
        materi,
        quiz,
    }
}
