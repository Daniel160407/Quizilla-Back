package com.quizilla.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto {
    private Integer id;
    private String name;
    private String imageUrl;
    private Double points;
    private Boolean correctAnswer;

    public GroupDto(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public GroupDto(Integer id, String name, String imageUrl, Double points) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.points = points;
    }
}
