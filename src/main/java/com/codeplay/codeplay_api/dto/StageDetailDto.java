package com.codeplay.codeplay_api.dto;

import com.codeplay.codeplay_api.entity.Stage.StageType;
import lombok.Data;

@Data
public class StageDetailDto {

    private String idStage;
    private String namaStage;
    private String deskripsi;
    private StageType type;

    // Field konten: diisi dengan MateriContentDto atau QuestionContentDto
    private Object content;
}
