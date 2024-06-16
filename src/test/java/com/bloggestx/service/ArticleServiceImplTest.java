package com.bloggestx.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import com.bloggestx.dto.article.ArticleDto;
import com.bloggestx.model.Article;
import com.bloggestx.model.User;
import com.bloggestx.model.enums.Role;
import com.bloggestx.model.enums.RoleDescription;
import com.bloggestx.repository.ArticleRepository;
import com.bloggestx.service.implementation.ArticleServiceImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Testing Article Service")
@ExtendWith(MockitoExtension.class)
public class ArticleServiceImplTest {

    @Mock
    private ArticleRepository articleRepository;
    @InjectMocks
    private ArticleServiceImpl articleService;

    private Article savedArticle;

    @BeforeEach
    public void setUp() {
        User user = User.builder()
                .id("1")
                .username("User")
                .password("Password")
                .role(Role.USER)
                .description(RoleDescription.USER)
                .build();

                 savedArticle = Article.builder()
                .id(String.valueOf(UUID.randomUUID()))
                .title("Article Title")
                .link("article-title")
                .body("Article Body")
                .author(user)
                .build();
    }




    @Test
    @DisplayName("saveArticle should return ArticleDto")
    public void ArticleService_saveArticle_ReturnsArticleDto() {
        when(articleRepository.save(any(Article.class))).thenReturn(savedArticle);

        ArticleDto articleDto = articleService.saveArticle(new Article());

        Assertions.assertNotNull(articleDto);
        Assertions.assertNotNull(articleDto.getId());

    }

    @Test
    @DisplayName("getArticlebyId should return Optional of Article with an Article")
    public void getArticlebyId_ReturnsOptionalArticle() {
        when(articleRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(savedArticle));

        Article foundArticle = articleService.getArticlebyId("id");

        Assertions.assertNotNull(foundArticle);
        Assertions.assertNotNull(foundArticle.getId());
    }

    @Test
    @DisplayName("getAll should return a Page of Articles")
    public void getAll_ReturnsPageArticles() {
        Page<Article> allArticles = new PageImpl<>(List.of(new Article()));
        when(articleRepository.findAll(any(Pageable.class))).thenReturn(allArticles);

        Page<Article> foundArticles = articleService.getAll(Pageable.unpaged());

        Assertions.assertNotNull(foundArticles);
        Assertions.assertEquals(allArticles.getTotalElements(), foundArticles.getTotalElements());
    }

    @Test
    @DisplayName("search should return a Page of Articles")
    public void search_ReturnsPageArticles() {
        Page<Article> allArticles = new PageImpl<>(List.of(new Article()));
        when(articleRepository.findByTitleContainsIgnoreCase(any(String.class), any(Pageable.class))).thenReturn(allArticles);

        Page<Article> foundArticles = articleService.search("", Pageable.unpaged());

        Assertions.assertNotNull(foundArticles);
        Assertions.assertEquals(allArticles.getTotalElements(), foundArticles.getTotalElements());
    }


    //https://medium.com/simform-engineering/testing-spring-boot-applications-best-practices-and-frameworks-6294e1068516
}
