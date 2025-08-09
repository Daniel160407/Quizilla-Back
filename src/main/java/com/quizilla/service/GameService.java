package com.quizilla.service;

import com.quizilla.dto.GameDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GameService {
    List<GameDto> getGames();

    List<GameDto> addGame(GameDto gameDto);
}
