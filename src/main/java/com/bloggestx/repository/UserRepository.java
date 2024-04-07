package com.bloggestx.repository;

import com.bloggestx.model.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends ElasticsearchRepository<User, String> {
    User findByUsername(String username);
    User save(User user);
    List<User> findAll();
}
