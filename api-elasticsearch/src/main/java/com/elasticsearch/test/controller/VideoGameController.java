package com.elasticsearch.test.controller;

import com.elasticsearch.test.model.VideoGameDTO;
import com.elasticsearch.test.repository.VideoGameRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoGameController {

    private final VideoGameRepository gameRepository;

    public VideoGameController(VideoGameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @GetMapping("api/v1/getGameByName/{gameName}")
    public VideoGameDTO getGameByName(@PathVariable String gameName) {
        return this.gameRepository.getVideoGameByName(gameName);
    }

    @GetMapping("api/v1/getGameById/{id}")
    public VideoGameDTO getGameById(@PathVariable Long id) {
        return this.gameRepository.getVideoGameByName("");
    }
}