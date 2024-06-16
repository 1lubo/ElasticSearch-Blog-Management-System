package com.bloggestx.configuration;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.lang.NonNull;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.Duration;

@Configuration
@Slf4j
@EnableElasticsearchRepositories(basePackages = "com.bloggestx")
public class ElasticSearchConfig extends ElasticsearchConfiguration {
    @Value("${host.and.port}")
    private String hostAndPort;
    @Value("${spring.elasticsearch.username}")
    private String username;
    @Value("${spring.elasticsearch.password}")
    private String password;
    @Value("classpath:keystore/http.p12")
    private File resourceFile;

    private final Logger logger = LoggerFactory.getLogger(ElasticSearchConfig.class);

    @Override
    @NonNull
    public ClientConfiguration clientConfiguration() {
        try {
            return ClientConfiguration.builder()
                    .connectedTo(hostAndPort)
                    .usingSsl(getSSLContext())
                    .withConnectTimeout(Duration.ofSeconds(5))
                    .withSocketTimeout(Duration.ofSeconds(3))
                    .withBasicAuth(username, password)
                    .build();
        } catch (KeyStoreException | KeyManagementException | NoSuchAlgorithmException | UnrecoverableKeyException |
                 IOException e) {
            logger.error("Unable to create Elasticsearch client configuration", e);
            throw new RuntimeException(e);
        }
    }

    private KeyStore getKeyStore() throws KeyStoreException, IOException {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(resourceFile);
        } catch (FileNotFoundException e) {
            logger.error("Unable to find Elasticsearch certificate file", e);
        }

        try {
            keyStore.load(inputStream, "".toCharArray());
        } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
            logger.error("Unable to load Elasticsearch certificate file", e);
        } finally {
            assert inputStream != null;
            inputStream.close();
        }
        return keyStore;
    }

    private SSLContext getSSLContext() throws KeyStoreException, IOException, UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyManagementException {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm()
        );
        trustManagerFactory.init(getKeyStore());

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
        return sslContext;
    }
}
