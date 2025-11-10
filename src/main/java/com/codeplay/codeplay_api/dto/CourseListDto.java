package com.codeplay.codeplay_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseListDto {

    private String idCourse;
    private String namaCourses;
    private String deskripsi;
    private Long totalLessons;
}
