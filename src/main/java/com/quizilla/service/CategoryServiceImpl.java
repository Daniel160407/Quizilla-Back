package com.quizilla.service;

import com.quizilla.dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Override
    public List<CategoryDto> getCategories() {
        return List.of();
    }

    @Override
    public List<CategoryDto> addCategory(CategoryDto categoryDto) {
        return List.of();
    }
}
