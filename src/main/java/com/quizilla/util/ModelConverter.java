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
                        .id(quiz.getId())
                        .categoryId(quiz.getCategoryId())
                        .type(quiz.getType())
                        .question(quiz.getQuestion())
                        .answer(quiz.getAnswer())
                        .mediaUrl(quiz.getMediaUrl())
                        .points(quiz.getPoints())
                        .hint(quiz.getHint())
                        .enabled(quiz.getEnabled())
                        .build()
        ));
        return quizDtos;
    }

    public List<GroupDto> convertGroupsToDtoList(List<Group> groups) {
        List<GroupDto> groupDtos = new ArrayList<>();
        groups.forEach(group -> groupDtos.add(new GroupDto(group.getId(), group.getName(), group.getImageUrl(), group.getPoints())));
        return groupDtos;
    }

    public List<CategoryDto> convertCategoriesToDtoList(List<Category> categories) {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        categories.forEach(category -> categoryDtos.add(new CategoryDto(category.getId(), category.getName())));
        return categoryDtos;
    }

    public Quiz convert(QuizDto quizDto, Integer enabled) {
        return Quiz.builder()
                .categoryId(quizDto.getCategoryId())
                .type(quizDto.getType())
                .question(quizDto.getQuestion())
                .answer(quizDto.getAnswer())
                .mediaUrl(quizDto.getMediaUrl())
                .points(quizDto.getPoints())
                .hint(quizDto.getHint())
                .enabled(enabled)
                .build();
    }

    public Group convert(GroupDto groupDto) {
        return Group.builder()
                .id(groupDto.getId())
                .name(groupDto.getName())
                .imageUrl(groupDto.getImageUrl())
                .points(groupDto.getPoints())
                .build();
    }

    public Category convert(CategoryDto categoryDto) {
        return Category.builder()
                .name(categoryDto.getName())
                .build();
    }
}
