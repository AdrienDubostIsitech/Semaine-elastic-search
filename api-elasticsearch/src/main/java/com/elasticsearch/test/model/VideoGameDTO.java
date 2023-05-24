package com.elasticsearch.test.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.thymeleaf.util.StringUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "video_games")
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
        creatorVisibility = JsonAutoDetect.Visibility.NONE
)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoGameDTO {

    @JsonProperty("Console")
    @NotNull
    @NotBlank
    private String console;

    @JsonProperty("GameName")
    @NotNull
    @NotBlank
    private String gameName;

    @JsonProperty("Score")
    @NotNull
    @NotBlank
    @Size(min = 0, max = 10)
    private Long score;

    @NotNull
    @NotBlank
    @JsonProperty("Review")
    private String review;

    public VideoGameDTO merge(VideoGameDTO vgToMerge) {
        if (!StringUtils.isEmpty(vgToMerge.getGameName())
                || !StringUtils.equals(this.getGameName(), vgToMerge.getGameName())) {
            this.setGameName(vgToMerge.getGameName());
        }

        if (!StringUtils.isEmpty(vgToMerge.getConsole())
                || !StringUtils.equals(this.getConsole(), vgToMerge.getConsole())) {
            this.setConsole(vgToMerge.getConsole());
        }

        if (!StringUtils.isEmpty(vgToMerge.getReview())
                || !StringUtils.equals(this.getReview(), vgToMerge.getReview())) {
            this.setReview(vgToMerge.getReview());
        }

        if ( vgToMerge.getScore() != null
                || this.getScore() != vgToMerge.getScore() ){
            this.setScore(vgToMerge.getScore());
        }

        return this;

    }

    public boolean isValid() {
        if (StringUtils.isEmpty(this.getGameName())) return false;
        if (StringUtils.isEmpty(this.getConsole())) return false;
        if (StringUtils.isEmpty(this.getReview())) return false;
        if (this.getScore() == null || this.getScore() < 0 || this.getScore() > 10) return false;
        return true;
    }
}
