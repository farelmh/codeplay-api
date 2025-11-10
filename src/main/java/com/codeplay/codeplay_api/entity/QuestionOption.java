package com.codeplay.codeplay_api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "question_option")
@Data
public class QuestionOption {

    @Id
    @Column(name = "id_question_option", length = 50)
    private String idQuestionOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_question")
    private Question question;

    @Column(name = "option_text", length = 100)
    private String optionText;

    @Column(name = "is_correct", columnDefinition = "TINYINT(1)")
    private Boolean isCorrect;
}
