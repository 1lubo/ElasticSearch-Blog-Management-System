package com.bloggestx.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.lang.NonNull;

import java.time.Duration;

@Configuration
@Slf4j
@EnableElasticsearchRepositories(basePackages = "com.example.bloggestx")
public class ElasticSearchConfig extends ElasticsearchConfiguration {
    @Override
    @NonNull
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .usingSsl("4b5074772ea4a228a0790474dc462a81ef3e6a8499e26f0fa4eca8a8dfdafce7")
                .withConnectTimeout(Duration.ofSeconds(5))
                .withSocketTimeout(Duration.ofSeconds(3))
                .withBasicAuth("elastic", "fD2PVVSrGdEkIjVTo_Ss")
                .build();
    }
}
