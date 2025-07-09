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

    public GroupDto(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
