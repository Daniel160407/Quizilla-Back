package com.quizilla.controller;

import com.quizilla.dto.CategoryDto;
import com.quizilla.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/quizilla/category")
@CrossOrigin(origins = "*")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<?> getCategories(@RequestParam Integer gameid) {
        List<CategoryDto> categoryDtos = categoryService.getCategories(gameid);
        return ResponseEntity.ok(categoryDtos);
    }

    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody CategoryDto categoryDto) {
        List<CategoryDto> categoryDtos = categoryService.addCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDtos);
    }

    @PutMapping
    public ResponseEntity<?> editCategory(@RequestBody CategoryDto categoryDto) {
        List<CategoryDto> categoryDtos = categoryService.editCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDtos);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCategory(@RequestParam Integer id) {
        List<CategoryDto> categoryDtos = categoryService.deleteCategory(id);
        return ResponseEntity.ok(categoryDtos);
    }
}
