package com.quizilla.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Integer id;
    private String name;

    public CategoryDto(String name) {
        this.name = name;
    }
}
