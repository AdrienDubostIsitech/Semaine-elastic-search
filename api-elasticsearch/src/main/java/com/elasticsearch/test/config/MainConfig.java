package com.elasticsearch.test.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.TransportUtils;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.elasticsearch.test.repository")
@Slf4j
public class MainConfig {

    private final String login = "elastic";
    private String password = "M_kEfFf0HA-gcAWbPQ2f";

    @Bean
    public RestClient getRestClient() {
        String fingerPrint = "4f7ba5d8139e6be4930712b34bc063785bb74027f97835a45d96988aaf1ce92d";
        SSLContext sslContext = TransportUtils.sslContextFromCaFingerprint(fingerPrint);


        BasicCredentialsProvider credsProv = new BasicCredentialsProvider();
        credsProv.setCredentials(
                AuthScope.ANY, new UsernamePasswordCredentials(login, password)
        );

        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200, "https"))
                .setHttpClientConfigCallback(hc -> hc
                        .setSSLContext(sslContext)
                        .setDefaultCredentialsProvider(credsProv))
                .build();
        return restClient;
    }

    @Bean
    public ElasticsearchTransport getElasticsearchTransport() throws IOException {
        return new RestClientTransport(
                getRestClient(), new JacksonJsonpMapper());
    }


    @Bean
    public ElasticsearchClient getElasticsearchClient() throws IOException {
        ElasticsearchClient client = new ElasticsearchClient(getElasticsearchTransport());
        return client;
    }

}
