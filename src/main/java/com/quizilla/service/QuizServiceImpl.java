package com.quizilla.service;

import com.quizilla.dto.QuizDto;
import com.quizilla.model.Quiz;
import com.quizilla.repository.QuizRepository;
import com.quizilla.util.ModelConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public List<QuizDto> getQuizzes(Integer gameId) {
        List<Quiz> quizzes = quizRepository.findAllByGameId(gameId);
        return modelConverter.convertQuizzesToDtoList(sortQuizzesByType(quizzes));
    }

    @Override
    public List<QuizDto> addQuiz(QuizDto quizDto) {
        Quiz convertedQuiz = modelConverter.convert(quizDto, 0);
        quizRepository.save(convertedQuiz);

        List<Quiz> quizzes = quizRepository.findAllByGameId(quizDto.getGameId());
        return modelConverter.convertQuizzesToDtoList(sortQuizzesByType(quizzes));
    }

    @Override
    public List<QuizDto> editQuiz(QuizDto quizDto) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizDto.getId());
        quizOptional.ifPresent(quiz -> {
            quiz.setCategoryId(quizDto.getCategoryId());
            quiz.setType(quizDto.getType());
            quiz.setQuestion(quizDto.getQuestion());
            quiz.setAnswer(quizDto.getAnswer());
            quiz.setMediaUrl(quizDto.getMediaUrl());
            quiz.setPoints(quizDto.getPoints());
            quiz.setHint(quizDto.getHint());
            quizRepository.save(quiz);
        });

        List<Quiz> quizzes = quizRepository.findAllByGameId(quizDto.getGameId());
        return modelConverter.convertQuizzesToDtoList(sortQuizzesByType(quizzes));
    }

    @Override
    public void enableQuiz(Integer id, Boolean enable) {
        Optional<Quiz> quizOptional = quizRepository.findById(id);
        quizOptional.ifPresent(quiz -> {
            Integer enabled = enable ? 0 : 1;
            quiz.setEnabled(enabled);
            quizRepository.save(quiz);
        });
    }

    @Override
    @Transactional
    public List<QuizDto> deleteQuiz(Integer id) {
        Integer gameId = quizRepository.findById(id).get().getGameId();
        quizRepository.deleteById(id);

        List<Quiz> quizzes = quizRepository.findAllByGameId(gameId);
        return modelConverter.convertQuizzesToDtoList(sortQuizzesByType(quizzes));
    }

    private List<Quiz> sortQuizzesByType(List<Quiz> quizzes) {
        quizzes.sort((q1, q2) -> {
            String t1 = q1.getType();
            String t2 = q2.getType();
            return t1.compareToIgnoreCase(t2);
        });
        return quizzes;
    }
}
