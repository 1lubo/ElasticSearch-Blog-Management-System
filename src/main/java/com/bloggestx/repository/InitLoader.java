package com.bloggestx.repository;

import com.bloggestx.model.Article;
import com.bloggestx.model.User;
import com.bloggestx.model.enums.Role;
import com.bloggestx.model.enums.RoleDescription;
import com.bloggestx.service.ArticleService;
import com.bloggestx.service.BlogUserDetailsService;
import com.bloggestx.service.implementation.ArticleServiceImpl;
import com.github.javafaker.Faker;
import com.github.javafaker.service.RandomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InitLoader implements CommandLineRunner {
    private final ArticleServiceImpl articleService;
    private final BlogUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public InitLoader(final ArticleServiceImpl articleService, final BlogUserDetailsService userDetailsService) {
        this.articleService = articleService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Value("#{new Boolean('${load.init.data}')}")
    private Boolean loadInitData;

    private static final Logger logger = LoggerFactory.getLogger(InitLoader.class);

    @Override
    public void run(String... args) {
        logger.info("CommandLineRunner#run initial data load");

        try {
            if(loadInitData){
                articleService.deleteAll();
                List<User> allUsers = userDetailsService.findAll();
                for (int i = 0; i < 20; i++) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(2000, Calendar.JANUARY, 0);
                    Date dateFrom = calendar.getTime();
                    Date dateTo = new Date();
                    Faker faker = new Faker(new Locale("en-US")
                            , new RandomService());

                    userDetailsService.save(new User(UUID.randomUUID().toString(), faker.ancient().titan(),
                            passwordEncoder.encode("password"), Role.USER, RoleDescription.USER));

                    Article article = new Article();
                    article.setTitle(faker.book().title());
                    article.setLink(String.join("-", article.getTitle().toLowerCase().split(" ")));
                    article.setSummary(faker.dune().saying());
                    List<String> lorem = faker.lorem().sentences(5);
                    article.setBody(String.join(" ", lorem));
                    article.setCreatedDate(faker.date().between(dateFrom, dateTo));
                    article.setAuthor(allUsers
                            .get(
                                    faker.random().nextInt(0, allUsers.size() - 1)
                            ));
                    articleService.saveArticle(article);
                }
            }
        } catch (Throwable throwable) {
            logger.error(throwable.toString());
        }

    }
}
