package com.quizilla.util;

import com.quizilla.dto.CategoryDto;
import com.quizilla.dto.GroupDto;
import com.quizilla.dto.QuizDto;
import com.quizilla.model.Category;
import com.quizilla.model.Group;
import com.quizilla.model.Quiz;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ModelConverter {
    public List<QuizDto> convertQuizzesToDtoList(List<Quiz> quizzes) {
        List<QuizDto> quizDtos = new ArrayList<>();
        quizzes.forEach(quiz -> quizDtos.add(
                QuizDto.builder()
                        .categoryId(quiz.getCategoryId())
                        .type(quiz.getType())
                        .question(quiz.getQuestion())
                        .answer(quiz.getAnswer())
                        .mediaUrl(quiz.getMediaUrl())
                        .points(quiz.getPoints())
                        .hint(quiz.getHint())
                        .build()
        ));
        return quizDtos;
    }

    public List<GroupDto> convertGroupsToDtoList(List<Group> groups) {
        List<GroupDto> groupDtos = new ArrayList<>();
        groups.forEach(group -> groupDtos.add(new GroupDto(group.getName(), group.getPoints())));
        return groupDtos;
    }

    public List<CategoryDto> convertCategoriesToDtoList(List<Category> categories) {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        categories.forEach(category -> categoryDtos.add(new CategoryDto(category.getId(), category.getName())));
        return categoryDtos;
    }

    public Quiz convert(QuizDto quizDto) {
        return Quiz.builder()
                .categoryId(quizDto.getCategoryId())
                .type(quizDto.getType())
                .question(quizDto.getQuestion())
                .answer(quizDto.getAnswer())
                .mediaUrl(quizDto.getMediaUrl())
                .points(quizDto.getPoints())
                .hint(quizDto.getHint())
                .build();
    }

    public Group convert(GroupDto groupDto) {
        return Group.builder()
                .name(groupDto.getName())
                .points(groupDto.getPoints())
                .build();
    }

    public Category convert(CategoryDto categoryDto) {
        return Category.builder()
                .name(categoryDto.getName())
                .build();
    }
}
