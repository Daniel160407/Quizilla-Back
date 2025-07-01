package com.quizilla.controller;

import com.quizilla.dto.QuizDto;
import com.quizilla.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/quizilla/quiz")
@CrossOrigin(origins = "*")
public class QuizController {
    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public ResponseEntity<?> getQuizzes() {
        List<QuizDto> quizDtos = quizService.getQuizzes();
        return ResponseEntity.ok(quizDtos);
    }

    @PostMapping
    public ResponseEntity<?> addQuiz(@RequestBody QuizDto quizDto) {
        List<QuizDto> quizDtos = quizService.addQuiz(quizDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(quizDtos);
    }
}
