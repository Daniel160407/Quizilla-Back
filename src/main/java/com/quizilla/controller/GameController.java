package com.quizilla.controller;

import com.quizilla.dto.GameDto;
import com.quizilla.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/quizilla/game")
@CrossOrigin(origins = "*")
public class GameController {
    private GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public ResponseEntity<?> getGames() {
        List<GameDto> gameDtos = gameService.getGames();
        return ResponseEntity.ok(gameDtos);
    }

    @PostMapping
    public ResponseEntity<?> addGame(@RequestBody GameDto gameDto) {
        List<GameDto> gameDtos = gameService.addGame(gameDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(gameDtos);
    }
}
