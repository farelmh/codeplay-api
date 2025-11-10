package com.codeplay.codeplay_api.repository;

import com.codeplay.codeplay_api.entity.Stage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StageRepository extends JpaRepository<Stage, String> {
    List<Stage> findByLesson_IdLesson(String idLesson);
}
