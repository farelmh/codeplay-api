package com.codeplay.codeplay_api.repository;

import com.codeplay.codeplay_api.entity.StageAnswer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StageAnswerRepository extends JpaRepository<StageAnswer, String> {
    Optional<StageAnswer> findByUser_IdUserAndQuestion_IdQuestion(String idUser, String idQuestion);
}