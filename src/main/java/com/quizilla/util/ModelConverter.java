package com.quizilla.util;

import com.quizilla.dto.CategoryDto;
import com.quizilla.dto.GameDto;
import com.quizilla.dto.GroupDto;
import com.quizilla.dto.QuizDto;
import com.quizilla.model.Category;
import com.quizilla.model.Game;
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

        for (Group group : groups) {
            GroupDto.GroupDtoBuilder builder = GroupDto.builder()
                    .id(group.getId())
                    .name(group.getName())
                    .imageUrl(group.getImageUrl())
                    .points(group.getPoints());

            int correctAnswer = group.getCorrectAnswer();
            if (correctAnswer == 0) {
                builder.correctAnswer(true);
            } else if (correctAnswer == 1) {
                builder.correctAnswer(false);
            }

            groupDtos.add(builder.build());
        }

        return groupDtos;
    }


    public List<CategoryDto> convertCategoriesToDtoList(List<Category> categories) {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        categories.forEach(category -> categoryDtos.add(new CategoryDto(category.getId(), category.getGameId(), category.getName())));
        return categoryDtos;
    }

    public Quiz convert(QuizDto quizDto, Integer enabled) {
        return Quiz.builder()
                .gameId(quizDto.getGameId())
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
                .gameId(categoryDto.getGameId())
                .name(categoryDto.getName())
                .build();
    }

    public GroupDto convert(Group group) {
        GroupDto.GroupDtoBuilder builder = GroupDto.builder()
                .id(group.getId())
                .name(group.getName())
                .imageUrl(group.getImageUrl())
                .points(group.getPoints());

        if (group.getCorrectAnswer() == 0) {
            builder.correctAnswer(true);
        } else if (group.getCorrectAnswer() == 1) {
            builder.correctAnswer(false);
        }

        return builder.build();
    }

    public Game convert(GameDto gameDto) {
        return Game.builder()
                .name(gameDto.getName())
                .dateCreated(gameDto.getDateCreated())
                .build();
    }

    public List<GameDto> convertGamesToDtoList(List<Game> games) {
        List<GameDto> gameDtos = new ArrayList<>();
        games.forEach(game -> gameDtos.add(new GameDto(game.getId(), game.getName(), game.getDateCreated())));

        return gameDtos;
    }
}
