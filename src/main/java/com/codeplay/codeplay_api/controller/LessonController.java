package com.codeplay.codeplay_api.controller;

import com.codeplay.codeplay_api.dto.StageDetailDto;
import com.codeplay.codeplay_api.service.CourseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    @Autowired
    private CourseService courseService;

    // GET /api/lessons/{idLesson}/stages: Detail Stages (Konten Materi/Kuis)
    @GetMapping("/{idLesson}/stages")
    public ResponseEntity<List<StageDetailDto>> getStagesDetail(
        @PathVariable("idLesson") String idLesson
    ) {
        try {
            List<StageDetailDto> stages =
                courseService.getStagesDetailByLessonId(idLesson);
            return ResponseEntity.ok(stages);
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // TODO: Tambahkan endpoint untuk submit jawaban, pengurangan energi, dan pencatatan progres di sini.
}
