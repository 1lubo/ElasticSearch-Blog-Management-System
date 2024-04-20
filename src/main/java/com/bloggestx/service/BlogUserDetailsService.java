package com.bloggestx.service;

import com.bloggestx.model.principal.BlogUserPrincipal;
import com.bloggestx.model.User;
import com.bloggestx.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public BlogUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public void save(User user){
        userRepository.save(user);
    }

    public List<User> findAll() { return userRepository.findAll(); }

    public User findByUserName(String username) { return userRepository.findByUsername(username); }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null){
          throw new UsernameNotFoundException(username);
        }
        return new BlogUserPrincipal(user);
    }
}
