package com.codeplay.codeplay_api.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_stage_progress")
@Data

public class UserStageProgress {
    @Id
    @Column(name = "id_progress", length = 50)
    private String idProgress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_stage")
    private Stage stage;

    @Enumerated(EnumType.STRING)
    private ProgressStatus status;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "completion_date")
    private LocalDateTime completionDate;

    @Column(name = "score", precision = 5, scale = 2)
    private BigDecimal score;

    public enum ProgressStatus {
        belum_mulai,
        proses,
        selesai
    }
}
