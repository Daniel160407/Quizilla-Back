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
    private Integer id;
    private Integer categoryId;
    private String type;
    private String question;
    private String answer;
    private String mediaUrl;
    private Double points;
    private String hint;

    public QuizDto(Integer categoryId, String type, String question, String answer, String mediaUrl, Double points, String hint) {
        this.categoryId = categoryId;
        this.type = type;
        this.question = question;
        this.answer = answer;
        this.mediaUrl = mediaUrl;
        this.points = points;
        this.hint = hint;
    }
}
