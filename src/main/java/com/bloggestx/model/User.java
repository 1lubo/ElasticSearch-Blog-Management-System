package com.bloggestx.model;

import com.bloggestx.model.enums.Role;
import com.bloggestx.model.enums.RoleDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private Role role;
    private RoleDescription description;

    @Field(type = FieldType.Keyword)
    @Builder.Default
    private String type = "user";

    public User(String id, String username, String password, Role role, RoleDescription description) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.description = description;
    }

}
