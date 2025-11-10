package com.codeplay.codeplay_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "courses")
@Data
public class Courses {

    @Id
    @Column(name = "id_courses", length = 50)
    private String idCourse;

    @Column(name = "nama_courses", length = 100)
    private String namaCourses;

    private String deskripsi;

    @OneToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Lesson> lessons;
}
