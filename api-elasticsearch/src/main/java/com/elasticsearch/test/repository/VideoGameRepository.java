package com.elasticsearch.test.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.elasticsearch.test.model.VideoGameDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class VideoGameRepository {

    @Autowired
    private ElasticsearchClient ELASTICSEARCH_CLIENT;

    private final String indexName = "video_games";


    public VideoGameDTO getVideoGameById(String id) {
        if (StringUtils.isEmpty(id)) return null;

        VideoGameDTO searchedGame = new VideoGameDTO();
        GetRequest getRequest = new GetRequest.Builder()
                .index(this.indexName)
                .id(id)
                .build();
        try {
            GetResponse<VideoGameDTO> response = this.ELASTICSEARCH_CLIENT.get(getRequest, VideoGameDTO.class);

            if (response == null) {
                log.info("la réponse est null");
                return null;
            }
            if (response.source() == null) {
                log.info("La réponse est vide");
                return null;
            }

            searchedGame = response.source();

        }
        catch (IOException exception) {
            log.error("Exception while searching by Id");
        }

        return searchedGame;
    }
    public VideoGameDTO getVideoGameByName(String videoGameName) {
        if (StringUtils.isEmpty(videoGameName)) return null;

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

        VideoGameDTO returnedGame = new VideoGameDTO();
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
    public List<VideoGameDTO> getVideoGamesByConsole(String console) {
        if (StringUtils.isEmpty(console)) return null;

        SearchResponse<VideoGameDTO> videoGameResponse = null;
        try {
            videoGameResponse = this.ELASTICSEARCH_CLIENT.search(s -> s
                            .index(this.indexName)
                            .size(100)
                            .query(q -> q
                                    .match(t -> t
                                            .field("Console")
                                            .query(console))
                            ),
                    VideoGameDTO.class
            );
            log.info(videoGameResponse.toString());
        } catch (IOException e) {
            log.error("Exception while searching profile", e);
        }

        List<VideoGameDTO> returnedGame = new ArrayList<>();
        if (videoGameResponse == null) {
            log.info("la réponse est null");
            return new ArrayList<>();
        }

        if (CollectionUtils.isEmpty(videoGameResponse.hits().hits())) {
            log.info("la réponse est vide");
            return new ArrayList<>();
        }

        List<Hit<VideoGameDTO>> hits = videoGameResponse.hits().hits();
        for (Hit<VideoGameDTO> hit : hits) {
            VideoGameDTO tmpGame = hit.source();
            if (tmpGame == null) continue;
            returnedGame.add(tmpGame);
        }
        return returnedGame;
    }
    public VideoGameDTO createVideoGame(VideoGameDTO newVideoGame) throws IOException {
        if (newVideoGame == null) return null;

        boolean isValid = newVideoGame.isValid();

        if (isValid == false) return null;

        IndexRequest<VideoGameDTO> indexRequest = new IndexRequest.Builder<VideoGameDTO>()
                .index(this.indexName)
                .document(newVideoGame)
                .build();
        IndexResponse response = this.ELASTICSEARCH_CLIENT.index(indexRequest);

        if (response == null) {
            log.info("La réponse est null");
            return null;
        }
        if (response.result() == null) {
            log.info("La réponse est vide");
            return null;
        }

        Result result = response.result();

        if (result == Result.Created) {
            log.info("Indexation du document : " + newVideoGame.toString());
        }
        else {
            log.info("Erreur lors de l'indéxation du document : " + newVideoGame.toString());
        }
        return newVideoGame;
    }
    public VideoGameDTO updateVideoGame(String id, VideoGameDTO updatedVideoGame) throws IOException {
        if (StringUtils.isEmpty(id)) return null;
        if (updatedVideoGame == null) return null;

        VideoGameDTO videoGameToUpdate = this.getVideoGameById(id);

        if (videoGameToUpdate == null) {
            log.info("Il y a eu une erreur lors de la recherche de votre jeu, il est possible qu'il n'existe pas ou que l'Id donné soit erroné");
            return null;
        }

        videoGameToUpdate.merge(updatedVideoGame);


        UpdateRequest updateRequest = new UpdateRequest.Builder<>()
                .id(id)
                .index(this.indexName)
                .doc(videoGameToUpdate)
                .build();

       UpdateResponse response = this.ELASTICSEARCH_CLIENT.update(updateRequest, VideoGameDTO.class);

       if (response == null) {
          log.info("La réponse est null");
          return null;
       }

       if (response.result() == null) {
           log.info("La réponse est vide");
           return null;
       }
       Result result = response.result();

       if (result == Result.Updated) {
           log.info("update du document : " + id + " resulat du document : " + videoGameToUpdate);
       }
       else {
           log.info("Error while updated document with id : " + id);
       }

       return videoGameToUpdate;
    }
    public Boolean deleteVideoGame(String id) throws IOException {
        Boolean deleted = false;
        if (StringUtils.isEmpty(id)) return deleted;

        DeleteRequest deleteRequest = new DeleteRequest.Builder()
                .index(this.indexName)
                .id(id)
                .build();

        DeleteResponse response = this.ELASTICSEARCH_CLIENT.delete(deleteRequest);

        if (response == null) {
            log.info("la réponse est null");
            return deleted;
        }

        if (response.result() == null) {
            log.info("la réponse est vide");
            return deleted;
        }

        Result result = response.result();

        if (result == Result.Deleted) {
            log.info("document supprimé : " + id);
            deleted = true;
        }
        else {
            log.info("Erreur pendant la suppression du document : " + id);
            deleted = false;
        }

        return deleted;

    }
}
