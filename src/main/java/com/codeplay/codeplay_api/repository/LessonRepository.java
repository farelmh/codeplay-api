package com.codeplay.codeplay_api.repository;

import com.codeplay.codeplay_api.entity.Lesson;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, String> {
    List<Lesson> findByCourse_IdCourse(String idCourse);
}
