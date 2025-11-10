package com.codeplay.codeplay_api.repository;

import com.codeplay.codeplay_api.entity.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionAnswerRepository
    extends JpaRepository<QuestionAnswer, String> {}
