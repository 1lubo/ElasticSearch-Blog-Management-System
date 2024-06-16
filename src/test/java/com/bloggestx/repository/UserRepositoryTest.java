package com.bloggestx.repository;


import com.bloggestx.configuration.ElasticSearchConfig;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@DisplayName("Testing userRepository")
@SpringBootTest
@Testcontainers
public class UserRepositoryTest {
    private static final String ELASTICSEARCH_VERSION = "8.12.2";

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(final @NotNull ConfigurableApplicationContext configurableApplicationContext) {

        }
    }

    @Container
    public static ElasticsearchContainer container = new ElasticsearchContainer(DockerImageName
            .parse("docker.elastic.co/elasticsearch/elasticsearch:8.12.2")
            .withTag(ELASTICSEARCH_VERSION));

    @Autowired
    UserRepository userRepository;

    @Autowired
    ElasticSearchConfig elasticSearchConfig;

    @BeforeEach
    void setUp() {
        container.start();
        System.setProperty("elasticsearch.host", container.getHost());
        System.setProperty("elasticsearch.port", String.valueOf(container.getMappedPort(9200)));
    }

    @AfterEach
    void tearDown() {
        container.stop();
    }

    @Test
    @DisplayName("save should save the user")
    void save() {

    }

}
