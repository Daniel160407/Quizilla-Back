package com.quizilla.service;

import com.quizilla.dto.QuizDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuizService {
    List<QuizDto> getQuizzes();

    List<QuizDto> addQuiz(QuizDto quizDto);
}
