package com.bloggestx.repository;

import com.bloggestx.model.Article;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends ElasticsearchRepository<Article, String> {

    @NonNull
    Optional<Article> findById(@NonNull String id);

    Page<Article> findByTitleContainsIgnoreCase(
            String query, Pageable pageable
    );
    Optional<Article> findByLink(String link);
}
