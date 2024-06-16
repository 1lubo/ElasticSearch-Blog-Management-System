package com.bloggestx.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "blog")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    @Id
    private String id;
    private String title;
    private String link;
    private String summary;
    private String body;

    @Field(type = FieldType.Nested)
    private User author;

    @Field(type = FieldType.Date)
    @Builder.Default
    private Date createdDate = new Date();

    @Field(type = FieldType.Keyword)
    @Builder.Default
    private String type = "article";

}
