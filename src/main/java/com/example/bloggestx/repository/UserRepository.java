package com.example.bloggestx.repository;

import com.example.bloggestx.model.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends ElasticsearchRepository<User, String> {
    User findByUsername(String username);

    public User save(User user);

    List<User> findAll();
}
