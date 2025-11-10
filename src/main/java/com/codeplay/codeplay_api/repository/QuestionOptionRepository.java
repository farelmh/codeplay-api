package com.codeplay.codeplay_api.repository;

import com.codeplay.codeplay_api.entity.QuestionOption;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionOptionRepository
    extends JpaRepository<QuestionOption, String> {
    List<QuestionOption> findByQuestion_IdQuestion(String idQuestion);
}
