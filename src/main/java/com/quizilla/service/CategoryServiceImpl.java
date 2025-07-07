package com.quizilla.service;

import com.quizilla.dto.CategoryDto;
import com.quizilla.model.Category;
import com.quizilla.repository.CategoryRepository;
import com.quizilla.util.ModelConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<CategoryDto> editCategory(CategoryDto categoryDto) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryDto.getId());
        categoryOptional.ifPresent(category -> {
            category.setName(categoryDto.getName());
            categoryRepository.save(category);
        });

        List<Category> categories = categoryRepository.findAll();
        return modelConverter.convertCategoriesToDtoList(categories);
    }

    @Override
    @Transactional
    public List<CategoryDto> deleteCategory(Integer id) {
        categoryRepository.deleteById(id);

        List<Category> categories = categoryRepository.findAll();
        return modelConverter.convertCategoriesToDtoList(categories);
    }
}
