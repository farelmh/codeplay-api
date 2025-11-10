package com.codeplay.codeplay_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "lesson")
@Data
public class Lesson {

    @Id
    @Column(name = "id_lesson", length = 50)
    private String idLesson;

    @Column(name = "nama_lesson", length = 100)
    private String namaLesson;

    private String deskripsi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_courses", referencedColumnName = "id_courses")
    private Courses course;
}
