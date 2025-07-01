package com.quizilla.service;

import com.quizilla.dto.QuizDto;
import com.quizilla.model.Quiz;
import com.quizilla.repository.QuizRepository;
import com.quizilla.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final ModelConverter modelConverter;

    @Autowired
    public QuizServiceImpl(QuizRepository quizRepository, ModelConverter modelConverter) {
        this.quizRepository = quizRepository;
        this.modelConverter = modelConverter;
    }

    @Override
    public List<QuizDto> getQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();
        return modelConverter.convertQuizzesToDtoList(quizzes);
    }

    @Override
    public List<QuizDto> addQuiz(QuizDto quizDto) {
        Quiz convertedQuiz = modelConverter.convert(quizDto);
        quizRepository.save(convertedQuiz);

        List<Quiz> quizzes = quizRepository.findAll();
        return modelConverter.convertQuizzesToDtoList(quizzes);
    }
}
