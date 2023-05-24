package com.elasticsearch.test.controller;

import com.elasticsearch.test.model.VideoGameDTO;
import com.elasticsearch.test.repository.VideoGameRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class VideoGameController {

    private final VideoGameRepository gameRepository;

    public VideoGameController(VideoGameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @GetMapping("api/v1/getGameById/{id}")
    public VideoGameDTO getGameById (@PathVariable String id) {
        return this.gameRepository.getVideoGameById(id);
    }

    @GetMapping("api/v1/getGameByName/{gameName}")
    public VideoGameDTO getGameByName(@PathVariable String gameName) {
        return this.gameRepository.getVideoGameByName(gameName);
    }

    @GetMapping("api/v1/getGameByConsole/{consoleName}")
    public List<VideoGameDTO> getGamesByConsole(@PathVariable String consoleName) {
        return this.gameRepository.getVideoGamesByConsole(consoleName);
    }

    @PostMapping("api/v1/createGame")
    public VideoGameDTO AddNewGame(@Valid @RequestBody VideoGameDTO newGame) throws IOException {
        return this.gameRepository.createVideoGame(newGame);
    }

    @PutMapping("api/v1/updateGame/{id}")
    public VideoGameDTO updateVideoGame(@PathVariable String id, @RequestBody VideoGameDTO newGameInfo) throws IOException {
        return this.gameRepository.updateVideoGame(id, newGameInfo);
    }

    @DeleteMapping("api/v1/deleteGame/{id}")
    public Boolean deleteVideoGame(@PathVariable String id) throws IOException {
        return this.gameRepository.deleteVideoGame(id);
    }
}
