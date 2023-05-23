package com.elasticsearch.test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "video_games")
public class VideoGameDTO {

    @JsonProperty("Console")
    private String console;

    @JsonProperty("GameName")
    private String gameName;
    @JsonProperty("Score")
    private Long score;

    @JsonProperty("Review")
    private String review;
}
