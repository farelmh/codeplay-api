package com.codeplay.codeplay_api.repository;

import com.codeplay.codeplay_api.entity.UserStageProgress;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStageProgressRepository extends JpaRepository<UserStageProgress, String> {
    Optional<UserStageProgress> findByUser_IdUserAndStage_IdStage(String idUser, String idStage);
    
    List<UserStageProgress> findByUser_IdUserAndStage_Lesson_IdLesson(String idUser, String idLesson);
    @Query("SELECT COUNT(usp) FROM UserStageProgress usp " +
        "WHERE usp.user.idUser = :idUser AND usp.stage.lesson.idLesson = :idLesson " +
        "AND usp.status = 'selesai'")
    long countCompletedStagesForLesson(@Param("idUser") String idUser, @Param("idLesson") String idLesson);
}
