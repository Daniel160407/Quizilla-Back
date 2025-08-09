package com.quizilla.service;

import com.quizilla.dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<CategoryDto> getCategories(Integer gameId);

    List<CategoryDto> addCategory(CategoryDto categoryDto);

    List<CategoryDto> editCategory(CategoryDto categoryDto);

    List<CategoryDto> deleteCategory(Integer id);
}
