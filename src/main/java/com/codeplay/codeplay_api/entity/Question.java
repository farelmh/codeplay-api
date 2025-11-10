package com.codeplay.codeplay_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "question")
@Data
public class Question {

    @Id
    @Column(name = "id_question", length = 50)
    private String idQuestion;

    @Column(name = "content", length = 255)
    private String content;

    @Column(name = "answers_type", length = 255)
    private String answersType;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<QuestionOption> options;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<QuestionAnswer> correctAnswers;
}
