package com.quizilla.service;

import com.quizilla.dto.QuizDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {
    @Override
    public List<QuizDto> getQuizzes() {
        return List.of();
    }

    @Override
    public List<QuizDto> addQuiz(QuizDto quizDto) {
        return List.of();
    }
}
