package com.bloggestx.service;

import com.bloggestx.dto.article.ArticleDto;
import com.bloggestx.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ArticleService {

    ArticleDto saveArticle(Article article);
    void deleteArticle(String id);
    void deleteAll();
    Article getArticlebyId(String id);
    Article getArticlebyLink(String link);
    Page<Article> getAll(Pageable pageable);
    Page<Article> search(String query, Pageable pageable);

}
