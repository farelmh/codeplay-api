package com.codeplay.codeplay_api.dto;

import lombok.Data;

@Data
public class LessonListDto {

    private String idLesson;
    private String namaLesson;
    private String deskripsi;
    private String idCourse;

    public LessonListDto(
        String idLesson,
        String namaLesson,
        String deskripsi,
        String idCourse
    ) {
        this.idLesson = idLesson;
        this.namaLesson = namaLesson;
        this.deskripsi = deskripsi;
        this.idCourse = idCourse;
    }
}
