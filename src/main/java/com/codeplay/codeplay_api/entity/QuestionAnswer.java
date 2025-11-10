package com.codeplay.codeplay_api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "question_answer")
@Data
public class QuestionAnswer {

    @Id
    @Column(name = "id_question_answer")
    private String idQuestionAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_question")
    private Question question; // FK ke tabel question

    @Column(name = "correct_answer", length = 100)
    private String correctAnswer; // Bisa berisi ID opsi atau jawaban teks

    @Column(name = "alternative_answer", columnDefinition = "LONGTEXT")
    private String alternativeAnswer;
}
