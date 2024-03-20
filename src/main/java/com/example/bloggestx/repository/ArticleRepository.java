package com.example.bloggestx.repository;

import com.example.bloggestx.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends ElasticsearchRepository<Article, String> {

    Optional<Article> findById(String id);
    Page<Article> findByAuthor_Username(String name, Pageable pageable);
    Page<Article> findByTitleContainsIgnoreCase(
            String query, Pageable pageable
    );
    @Query("{\"bool\":{\"must\":[{\"match\":{\"author.username\": \"?0\"}}]}}")
    Page<Article> findByAuthorsNameUsingCustomQuery (String name, Pageable pageable);

    Optional<Article> findByLink(String link);
}
