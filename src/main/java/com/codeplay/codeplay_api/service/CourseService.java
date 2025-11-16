package com.codeplay.codeplay_api.service;

import com.codeplay.codeplay_api.dto.*;
import com.codeplay.codeplay_api.entity.*;
import com.codeplay.codeplay_api.entity.Stage.StageType;
import com.codeplay.codeplay_api.repository.*;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private StageRepository stageRepository;

    @Autowired
    private MateriRepository materiRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionOptionRepository questionOptionRepository;

    // --- 1. Logika Pengambilan Courses dengan Hitungan Lesson ---

    public List<CourseListDto> findAllCoursesWithLessonCount() {
        return coursesRepository.findCourseListWithLessonCount();
    }

    // --- 2. Logika Pengambilan Lessons dalam Course ---

    public List<LessonListDto> findLessonsByCourseId(String idCourse) {
        List<Lesson> lessons = lessonRepository.findByCourse_IdCourse(idCourse);

        return lessons
            .stream()
            .map(l ->
                new LessonListDto(
                    l.getIdLesson(),
                    l.getNamaLesson(),
                    l.getDeskripsi(),
                    l.getCourse().getIdCourse()
                )
            )
            .toList();
    }

    // --- 3. Logika Pengambilan Detail Stage (Kompleksitas di sini) ---

    public List<StageDetailDto> getStagesDetailByLessonId(String idLesson) {
        // Asumsi: Stages diambil sudah terurut. Jika tidak ada kolom order di DB,
        // harus diurutkan manual atau ditambahkan kolom order.
        List<Stage> stages = stageRepository.findByLesson_IdLessonOrderByNamaStageAsc(idLesson);

        if (stages.isEmpty()) {
            throw new EntityNotFoundException(
                "Lesson atau Stages tidak ditemukan untuk ID: " + idLesson
            );
        }

        List<StageDetailDto> results = new ArrayList<>();

        for (Stage stage : stages) {
            StageDetailDto dto = new StageDetailDto();
            dto.setIdStage(stage.getIdStage());
            dto.setNamaStage(stage.getNamaStage());
            dto.setDeskripsi(stage.getDeskripsi());
            dto.setType(stage.getType());
            // dto.setStageOrder(stage.getStageOrder()); // Jika ada

            if (stage.getType() == StageType.materi) {
                Optional<Materi> materi = materiRepository.findById(
                    stage.getIdMateri()
                );
                materi.ifPresent(m -> {
                    MateriContentDto materiDto = new MateriContentDto();
                    materiDto.setIdMateri(m.getIdMateri());
                    materiDto.setKonten(m.getKonten());
                    materiDto.setFileUrl(m.getFileUrl());
                    dto.setContent(materiDto);
                });
            } else if (stage.getType() == StageType.quiz) {
                Optional<Question> question = questionRepository.findById(
                    stage.getIdQuestion()
                );
                question.ifPresent(q -> {
                    QuestionContentDto questionDto = new QuestionContentDto();
                    questionDto.setIdQuestion(q.getIdQuestion());
                    questionDto.setContent(q.getContent());
                    questionDto.setAnswersType(q.getAnswersType());

                    // Ambil Opsi Jawaban (JOIN ke QuestionOption)
                    List<QuestionOption> options =
                        questionOptionRepository.findByQuestion_IdQuestion(
                            q.getIdQuestion()
                        );
                    questionDto.setOptions(
                        options
                            .stream()
                            .map(this::mapToSafeOption) // Hapus kunci jawaban
                            .collect(Collectors.toList())
                    );

                    dto.setContent(questionDto);
                });
            }
            results.add(dto);
        }

        return results;
    }

    // Metode utilitas untuk menyembunyikan kunci jawaban dari client
    private QuestionOption mapToSafeOption(QuestionOption option) {
        // Buat objek baru agar Entity aslinya tidak diubah
        QuestionOption safeOption = new QuestionOption();
        safeOption.setIdQuestionOption(option.getIdQuestionOption());
        safeOption.setOptionText(option.getOptionText());
        safeOption.setIsCorrect(null); // Set ke null sebelum dikirim
        return safeOption;
    }
}
