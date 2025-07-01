package com.quizilla.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {
    private Integer categoryId;
    private String type;
    private String question;
    private String answer;
    private String mediaUrl;
    private Double points;
    private String hint;
}
