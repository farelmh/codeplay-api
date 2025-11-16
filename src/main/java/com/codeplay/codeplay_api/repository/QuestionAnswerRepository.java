package com.codeplay.codeplay_api.repository;

import com.codeplay.codeplay_api.entity.Question;
import com.codeplay.codeplay_api.entity.QuestionAnswer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionAnswerRepository
    extends JpaRepository<QuestionAnswer, String> {

    Optional<Question> findByQuestion_IdQuestion(String idQuestion);

}
