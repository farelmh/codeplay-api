package com.codeplay.codeplay_api.service;

import com.codeplay.codeplay_api.dto.StageAnswerRequestDto;
import com.codeplay.codeplay_api.dto.StageAnswerResponseDto;
import com.codeplay.codeplay_api.entity.*;
import com.codeplay.codeplay_api.repository.*;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StageAnswerService {

    private final StageAnswerRepository stageAnswerRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final UserStageProgressRepository userStageProgressRepository;
    private final StageRepository stageRepository;

    public StageAnswerResponseDto submitAnswer(
        String idUser,
        String idStage,
        String idQuestion,
        StageAnswerRequestDto dto
    ) {
        var user = userRepository.findById(idUser).orElseThrow();
        var question = questionRepository.findById(idQuestion).orElseThrow();
        var stage = stageRepository.findById(idStage).orElseThrow();
        
        boolean correct = false;

        if (question.getAnswersType().equals("quiz")) {
            var correctOption = question.getOptions().stream()
                .filter(o -> Boolean.TRUE.equals(o.getIsCorrect()))
                .findFirst()
                .orElse(null);

            if (correctOption != null) {
                correct = correctOption.getOptionText()
                .equalsIgnoreCase(dto.getJawabanUser());
            }
        } else if (question.getAnswersType().equals("essay")) {
            correct =  question.getCorrectAnswers()
            .stream()
            .anyMatch(ans
                -> ans.getCorrectAnswer().equalsIgnoreCase(dto.getJawabanUser()) ||
                (ans.getAlternativeAnswer() != null &&
                ans.getAlternativeAnswer().equalsIgnoreCase(dto.getJawabanUser()))
            );
        }

        BigDecimal scoreEarned = correct
                ? new BigDecimal("100.00")
                : BigDecimal.ZERO;

        var existingAnswer = stageAnswerRepository
            .findByUser_IdUserAndQuestion_IdQuestion(idUser, idQuestion);

        StageAnswer stageAnswer;

        if (existingAnswer.isEmpty()) {
            stageAnswer = new StageAnswer();
            stageAnswer.setIdAnswer(UUID.randomUUID().toString());
            stageAnswer.setUser(user);
            stageAnswer.setQuestion(question);
            stageAnswer.setSubmittedAt(LocalDateTime.now());
        } else {
            stageAnswer = existingAnswer.get();
            stageAnswer.setSubmittedAt(LocalDateTime.now());
        }
        
        stageAnswer.setJawabanUser(dto.getJawabanUser());
        stageAnswer.setIsCorrect(correct);
        stageAnswer.setScoreEarned(scoreEarned);

        stageAnswerRepository.save(stageAnswer);

        var progress = userStageProgressRepository
            .findByUser_IdUserAndStage_IdStage(idUser, idStage)
            .orElseGet(() ->{
                var pr = new UserStageProgress();
                pr.setIdProgress(UUID.randomUUID().toString());
                pr.setUser(user);
                pr.setStage(stage);
                pr.setStartTime(LocalDateTime.now());
                pr.setStatus(UserStageProgress.ProgressStatus.proses);
                return pr;
            });

        progress.setCompletionDate(LocalDateTime.now());
        progress.setScore(scoreEarned);
        progress.setStatus(UserStageProgress.ProgressStatus.selesai);
        userStageProgressRepository.save(progress);

        return new StageAnswerResponseDto(
            correct,
            scoreEarned,
            correct ? "Jawaban Benar!" : "Jawaban Salah!"
        );
    }
}
