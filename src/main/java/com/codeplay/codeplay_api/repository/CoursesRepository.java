package com.codeplay.codeplay_api.repository;

import com.codeplay.codeplay_api.dto.CourseListDto;
import com.codeplay.codeplay_api.entity.Courses;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursesRepository extends JpaRepository<Courses, String> {
    @Query(
        "SELECT NEW com.codeplay.codeplay_api.dto.CourseListDto(" +
            "c.idCourse, c.namaCourses, c.deskripsi, COUNT(l)) " +
            "FROM Courses c LEFT JOIN c.lessons l " +
            "GROUP BY c.idCourse, c.namaCourses, c.deskripsi"
    )
    List<CourseListDto> findCourseListWithLessonCount();
}
