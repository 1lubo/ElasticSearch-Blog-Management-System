package com.bloggestx.service;

import com.bloggestx.exception.ArticleNotFoundException;
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
import static org.mockito.Mockito.*;

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
    @DisplayName("getArticlebyId should return Optional of Article if Article exists")
    public void getArticlebyId_ReturnsOptionalArticle() {
        when(articleRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(savedArticle));

        Article foundArticle = articleService.getArticlebyId("id");

        Assertions.assertNotNull(foundArticle);
        Assertions.assertNotNull(foundArticle.getId());
    }

    @Test
    @DisplayName("getArticlebyId should throw ArticleNotFoundException if Article with given id does not exist")
    public void getArticlebyId_Throws_ArticleNotFoundException() {
        when (articleRepository.findById("doesNotExist")).thenThrow(ArticleNotFoundException.class);
        Assertions.assertThrows(ArticleNotFoundException.class, () -> articleService.getArticlebyId("doesNotExist"));
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

    @Test
    @DisplayName("deleteArticle should call articleRepostory 2 times if an Article with given ID exists")
    public void deleteArticle_calls_ArticleRepostory_TwoTimes() {

        when (articleRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(savedArticle));
        doNothing(). when (articleRepository).delete(savedArticle);

        articleService.deleteArticle(savedArticle.getId());

        verify(articleRepository, times(1)).findById(any(String.class));
        verify(articleRepository, times(1)).delete(savedArticle);
    }

    @Test
    @DisplayName("deleteArticle should throw ArticleNotFoundException if no Article with given ID exists")
    public void deleteArticle_throws_ArticleNotFoundException() {
        Assertions.assertThrows(ArticleNotFoundException.class, () -> articleService.deleteArticle("doesNotExist"));
    }

    @Test
    @DisplayName("getArticlebyLink should return Optional with Article if Article exists")
    public void getArticlebyLink_ReturnsOptionalArticle() {
        when(articleRepository.findByLink(any(String.class))).thenReturn(Optional.ofNullable(savedArticle));

        Article foundArticle = articleService.getArticlebyLink(savedArticle.getLink());

        Assertions.assertNotNull(foundArticle);
        Assertions.assertNotNull(foundArticle.getLink());
    }

    @Test
    @DisplayName("getArticlebyLink should throw ArticleNotFoundException if Article with given id does not exist")
    public void getArticlebyLink_Throws_ArticleNotFoundException() {
        Assertions.assertThrows(ArticleNotFoundException.class, () -> articleService.getArticlebyId("doesNotExist"));
    }

    @Test
    @DisplayName("deleteAll should call repository 1 time")
    public void deleteAll_calls_repository_OneTime() {
        doNothing(). when (articleRepository).deleteAll();

        articleService.deleteAll();

        verify(articleRepository, times(1)).deleteAll();
    }

    //https://medium.com/simform-engineering/testing-spring-boot-applications-best-practices-and-frameworks-6294e1068516
}
