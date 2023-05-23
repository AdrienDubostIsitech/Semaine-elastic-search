package com.elasticsearch.test.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.elasticsearch.test.model.VideoGameDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Collection;

@Repository
@Slf4j
public class VideoGameRepository {

    @Autowired
    private ElasticsearchClient ELASTICSEARCH_CLIENT = null;

    private final String indexName = "video_games";


    public VideoGameDTO getVideoGameByName(String videoGameName) {
        SearchResponse<VideoGameDTO> videoGameResponse = null;
        try {
            videoGameResponse = this.ELASTICSEARCH_CLIENT.search(s -> s
                            .index(this.indexName)
                            .size(10)
                            .query(q -> q
                                    .match(t -> t
                                            .field("GameName")
                                            .query(videoGameName))
                            ),
                    VideoGameDTO.class
            );
            log.info(videoGameResponse.toString());
        } catch (IOException e) {
            log.error("Exception while searching profile", e);
        }

        VideoGameDTO returnedGame =  new VideoGameDTO();
        if (videoGameResponse == null) {
            log.info("la réponse est null");
            return new VideoGameDTO();
        }

        if (CollectionUtils.isEmpty(videoGameResponse.hits().hits())) {
            log.info("la réponse est vide");
            return new VideoGameDTO();
        }

        returnedGame = videoGameResponse.hits().hits().get(0).source();
        return returnedGame;

    }
}
