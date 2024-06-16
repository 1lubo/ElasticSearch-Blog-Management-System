package com.bloggestx.repository;

import com.bloggestx.model.User;
import lombok.NonNull;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends ElasticsearchRepository<User, String> {
    User findByUsername(String username);
    @NonNull List<User> findAll();
}
