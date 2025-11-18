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

    @Autowired
    private UserStageProgressRepository userStageProgressRepository;


    public List<CourseListDto> findAllCoursesWithLessonCount() {
        return coursesRepository.findCourseListWithLessonCount();
    }

    public List<LessonListDto> findLessonsByCourseId(String idCourse, String idUser) {
        List<Lesson> lessons = lessonRepository.findByCourse_IdCourseOrderByIdLessonAsc(idCourse);

        List<LessonListDto> results = new ArrayList<>();
        
        boolean previousLessonIsCompleted = true; 

        for (Lesson lesson : lessons) {
            
            boolean isUnlocked = previousLessonIsCompleted; 
            
            LessonListDto dto = new LessonListDto(
                lesson.getIdLesson(),
                lesson.getNamaLesson(),
                lesson.getDeskripsi(),
                lesson.getCourse().getIdCourse(),
                isUnlocked
            );
            results.add(dto);

            previousLessonIsCompleted = isLessonCompleted(idUser, lesson.getIdLesson());
        }
        
        return results;
    }

    public boolean isLessonCompleted(String idUser, String idLesson) {
        long totalStages = stageRepository.countByLesson_IdLesson(idLesson);

        if (totalStages == 0) {
            return false;
        }

        long completedStages = userStageProgressRepository.countCompletedStagesForLesson(idUser, idLesson);

        return totalStages == completedStages;
    }

    public List<StageDetailDto> getStagesDetailByLessonId(String idLesson) {
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

                    List<QuestionOption> options =
                        questionOptionRepository.findByQuestion_IdQuestion(
                            q.getIdQuestion()
                        );
                    questionDto.setOptions(
                        options
                            .stream()
                            .map(this::mapToSafeOption)
                            .collect(Collectors.toList())
                    );

                    dto.setContent(questionDto);
                });
            }
            results.add(dto);
        }

        return results;
    }

    private QuestionOption mapToSafeOption(QuestionOption option) {
        QuestionOption safeOption = new QuestionOption();
        safeOption.setIdQuestionOption(option.getIdQuestionOption());
        safeOption.setOptionText(option.getOptionText());
        safeOption.setIsCorrect(null);
        return safeOption;
    }
}
