package com.bloggestx.service;

import com.bloggestx.model.Article;
import com.bloggestx.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository repository;

    public Optional<Article> findById (String id) {
        return repository.findById(id);
    }
    public Optional<Article> findByLink (String link) { return repository.findByLink(link); }
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public void save(Article article) {
        repository.save(article);
    }

    public void deleteAll() { repository.deleteAll(); }

    public Page<Article> getAll(Pageable pageable) { return repository.findAll(pageable); }

    public Page<Article> search(String query, Pageable pageable) {
        return repository.findByTitleContainsIgnoreCase(
                query, pageable
        ); }

}
