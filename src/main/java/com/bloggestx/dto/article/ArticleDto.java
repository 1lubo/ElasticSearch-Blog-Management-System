package com.bloggestx.dto.article;

import com.bloggestx.model.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
    private String id;
    private String title;
    private String link;

    public ArticleDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.link = article.getLink();
    }
}
