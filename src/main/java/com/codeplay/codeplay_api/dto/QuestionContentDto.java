package com.codeplay.codeplay_api.dto;

import com.codeplay.codeplay_api.entity.QuestionOption;
import java.util.List;
import lombok.Data;

@Data
public class QuestionContentDto {

    private String idQuestion;
    private String content;
    private String answersType;
    private List<QuestionOption> options;
}
