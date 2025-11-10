package com.codeplay.codeplay_api.controller;

import com.codeplay.codeplay_api.dto.CourseListDto;
import com.codeplay.codeplay_api.entity.Lesson;
import com.codeplay.codeplay_api.service.CourseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // GET /api/courses: Daftar Courses dengan Jumlah Lesson
    @GetMapping
    public ResponseEntity<List<CourseListDto>> getAllCourses() {
        List<CourseListDto> courses =
            courseService.findAllCoursesWithLessonCount();
        return ResponseEntity.ok(courses);
    }

    // GET /api/courses/{idCourse}/lessons: Daftar Lessons (Level) dalam Course
    @GetMapping("/{idCourse}/lessons")
    public ResponseEntity<List<Lesson>> getLessonsByCourse(
        @PathVariable("idCourse") String idCourse
    ) {
        List<Lesson> lessons = courseService.findLessonsByCourseId(idCourse);
        if (lessons.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lessons);
    }
}
