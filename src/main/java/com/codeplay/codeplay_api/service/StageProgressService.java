package com.codeplay.codeplay_api.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.codeplay.codeplay_api.dto.StageProgressResponseDto;
import com.codeplay.codeplay_api.entity.UserStageProgress;
import com.codeplay.codeplay_api.repository.StageRepository;
import com.codeplay.codeplay_api.repository.UserRepository;
import com.codeplay.codeplay_api.repository.UserStageProgressRepository;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StageProgressService {
    private final UserStageProgressRepository userStageProgressRepository;
    private final UserRepository userRepository;
    private final StageRepository stageRepository;

    public StageProgressResponseDto startProgress(String idUser, String idStage) {

        var user = userRepository.findById(idUser)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        var stage = stageRepository.findById(idStage)
                .orElseThrow(() -> new RuntimeException("Stage tidak ditemukan"));

        // Cek apakah progress sudah ada
        var existingProgress = userStageProgressRepository
                .findByUser_IdUserAndStage_IdStage(idUser, idStage)
                .orElse(null);

        // Jika belum ada, buat baru
        if (existingProgress == null) {
            var newProgress = new UserStageProgress();
            newProgress.setIdProgress(UUID.randomUUID().toString());
            newProgress.setUser(user);
            newProgress.setStage(stage);
            newProgress.setStartTime(LocalDateTime.now());
            newProgress.setStatus(UserStageProgress.ProgressStatus.proses);
            userStageProgressRepository.save(newProgress);

            return new StageProgressResponseDto(
                    newProgress.getIdProgress(),
                    newProgress.getStatus().toString(),
                    "Progress stage dimulai (baru dibuat)"
            );
        }

        // Jika sudah ada tapi belum selesai → update statusnya
        if (existingProgress.getStatus() != UserStageProgress.ProgressStatus.selesai) {
            existingProgress.setStatus(UserStageProgress.ProgressStatus.proses);

            if (existingProgress.getStartTime() == null) {
                existingProgress.setStartTime(LocalDateTime.now());
            }

            userStageProgressRepository.save(existingProgress);

            return new StageProgressResponseDto(
                    existingProgress.getIdProgress(),
                    existingProgress.getStatus().toString(),
                    "Progress stage sudah ada, status diperbarui ke PROSES"
            );
        }

        // Jika sudah selesai → tidak diubah
        return new StageProgressResponseDto(
                existingProgress.getIdProgress(),
                existingProgress.getStatus().toString(),
                "Stage sudah diselesaikan sebelumnya"
        );
    }

    public void completeStage(String idUser, String idStage) {
        var progress = userStageProgressRepository
                .findByUser_IdUserAndStage_IdStage(idUser, idStage)
                .orElseThrow(() -> new RuntimeException("Progress not started"));

        progress.setCompletionDate(LocalDateTime.now());
        progress.setStatus(UserStageProgress.ProgressStatus.selesai);

        userStageProgressRepository.save(progress);
    }
}
