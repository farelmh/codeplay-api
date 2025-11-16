package com.codeplay.codeplay_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeplay.codeplay_api.dto.StageAnswerRequestDto;
import com.codeplay.codeplay_api.dto.StageAnswerResponseDto;
import com.codeplay.codeplay_api.dto.StageProgressResponseDto;
import com.codeplay.codeplay_api.service.StageAnswerService;
import com.codeplay.codeplay_api.service.StageProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/api/lessons/{idLesson}/stages/{idStage}")
public class StageController {

    private final StageProgressService stageProgressService;

    private final StageAnswerService stageAnswerService;

    StageController(StageAnswerService stageAnswerService, StageProgressService stageProgressService) {
        this.stageAnswerService = stageAnswerService;
        this.stageProgressService = stageProgressService;
    }
    @PostMapping("/questions/{idQuestion}/answer")
    public ResponseEntity<StageAnswerResponseDto> submitAnswer(
        @PathVariable("idStage") String idStage,
        @PathVariable("idLesson") String idLesson,
        @PathVariable("idQuestion") String idQuestion,
        @RequestHeader("idUser") String idUser,
        @RequestBody StageAnswerRequestDto dto
    ) {
        return ResponseEntity.ok(
            stageAnswerService.submitAnswer(idUser, idStage, idQuestion, dto)
        );
    }

    @PostMapping("/start")
        public ResponseEntity<StageProgressResponseDto> startProgress(
            @PathVariable("idLesson") String idLesson,
            @PathVariable("idStage") String idStage,
            @RequestHeader("idUser") String idUser
        ) {
            return ResponseEntity.ok(stageProgressService.startProgress(idUser, idStage));
        }

    @PostMapping("/complete")
    public ResponseEntity<?> completeStage(
        @PathVariable("idStage") String idStage,
        @RequestHeader("idUser") String idUser
    ) {
        stageProgressService.completeStage(idUser, idStage);
        return ResponseEntity.ok().build();
    }
    
}
