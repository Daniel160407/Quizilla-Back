package com.quizilla.service;

import com.quizilla.dto.GameDto;
import com.quizilla.model.Game;
import com.quizilla.repository.GameRepository;
import com.quizilla.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final ModelConverter modelConverter;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, ModelConverter modelConverter) {
        this.gameRepository = gameRepository;
        this.modelConverter = modelConverter;
    }

    @Override
    public List<GameDto> getGames() {
        List<Game> games = gameRepository.findAll();
        return modelConverter.convertGamesToDtoList(games);
    }

    @Override
    public List<GameDto> addGame(GameDto gameDto) {
        LocalDate localDate = LocalDate.now();
        gameDto.setDateCreated(localDate.toString());

        Game convertedGame = modelConverter.convert(gameDto);
        gameRepository.save(convertedGame);

        List<Game> games = gameRepository.findAll();
        return modelConverter.convertGamesToDtoList(games);
    }
}
