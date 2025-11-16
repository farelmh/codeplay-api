package com.codeplay.codeplay_api.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "stage_answer")
@Data

public class StageAnswer {
    @Id
    @Column(name = "id_answer", length = 50)
    private String idAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_question")
    private Question question;

    @Column(name = "jawaban_user", columnDefinition = "TEXT")
    private String jawabanUser;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "score_earned", precision = 5, scale = 2)
    private BigDecimal scoreEarned;
}
