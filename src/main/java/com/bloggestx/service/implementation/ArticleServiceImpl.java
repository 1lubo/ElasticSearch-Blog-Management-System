package com.bloggestx.service.implementation;

import com.bloggestx.dto.article.ArticleDto;
import com.bloggestx.exception.ArticleNotFoundException;
import com.bloggestx.model.Article;
import com.bloggestx.repository.ArticleRepository;
import com.bloggestx.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public ArticleDto saveArticle(Article article) {
        ArticleDto articleDto = new ArticleDto();

        Article newArticle = articleRepository.save(article);

        articleDto.setId(newArticle.getId());
        articleDto.setLink(newArticle.getLink());
        articleDto.setTitle(newArticle.getTitle());

        return articleDto;
    }

    @Override
    public void deleteArticle(String id) {
        Article article = articleRepository.findById(id).orElseThrow(() ->
                new ArticleNotFoundException("Article could not be deleted"));
        articleRepository.delete(article);
    }

    @Override
    public void deleteAll() {
        articleRepository.deleteAll();
    }

    @Override
    public Article getArticlebyId(String id) {
        return articleRepository.findById(id).orElseThrow(() ->
                new ArticleNotFoundException("Article could not be found"));
    }

    @Override
    public Article getArticlebyLink(String link) {
        return articleRepository.findByLink(link).orElseThrow(() ->
                new ArticleNotFoundException("Article could not be found"));
    }

    @Override
    public Page<Article> getAll(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    @Override
    public Page<Article> search(String query, Pageable pageable) {
        return articleRepository.findByTitleContainsIgnoreCase(query, pageable);
    }
}
