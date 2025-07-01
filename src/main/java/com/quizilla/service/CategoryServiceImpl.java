package com.quizilla.service;

import com.quizilla.dto.CategoryDto;
import com.quizilla.model.Category;
import com.quizilla.repository.CategoryRepository;
import com.quizilla.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelConverter modelConverter;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelConverter modelConverter) {
        this.categoryRepository = categoryRepository;
        this.modelConverter = modelConverter;
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return modelConverter.convertCategoriesToDtoList(categories);
    }

    @Override
    public List<CategoryDto> addCategory(CategoryDto categoryDto) {
        Category convertedCategory = modelConverter.convert(categoryDto);
        categoryRepository.save(convertedCategory);

        List<Category> categories = categoryRepository.findAll();
        return modelConverter.convertCategoriesToDtoList(categories);
    }
}
