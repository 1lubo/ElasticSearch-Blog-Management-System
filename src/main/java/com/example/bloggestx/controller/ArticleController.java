package com.example.bloggestx.controller;

import com.example.bloggestx.exception.NotFoundException;
import com.example.bloggestx.model.Article;
import com.example.bloggestx.model.User;
import com.example.bloggestx.service.ArticleService;
import com.example.bloggestx.service.BlogUserDetailsService;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;
    private final BlogUserDetailsService userService;

    public ArticleController(ArticleService articleService, BlogUserDetailsService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model,
                        @AuthenticationPrincipal UserDetails userDetails,
                        @RequestParam(required = false, value = "status") Optional<Integer> status,
                        @RequestParam(required = false, value = "q") String q,
                        @RequestParam(required = false, value = "page") Optional<Integer> page,
                        @RequestParam(required = false, value = "size") Optional<Integer> size) {
        if (q == null) {
            model.addAttribute("articles", articleService
                    .getAll(PageRequest.of(page.orElse(1), size.orElse(10))));
        } else {
            model.addAttribute("articles", articleService
                    .search(q, (PageRequest.of(page.orElse(1), size.orElse(10)))));
        }

        if (status.isPresent()) {
            if (status.get().equals(200)) {
                model.addAttribute("message",
                        String.format("User { %s } successfully logged in.", userDetails.getUsername()));
            } else {
                model.addAttribute("message", "You have been logged out.");
            }
        }
            return "article/index";
    }



    @GetMapping("/show/{link}")
    public String getPost(@PathVariable String link, Model model) {
        Optional<Article> article = articleService.findByLink(link);
        if (article.isPresent()) {
            model.addAttribute("article", article.get());
        } else {
            throw new NotFoundException(link);
        }
        return "article/show";
    }

    @GetMapping("/new")
    public String newPost(){
        return "article/create";
    }

    @GetMapping("/show/edit/{id}")
    public String editPost(@PathVariable String id, Model model){
        Optional<Article> article = articleService.findById(id);
        if (article.isPresent()) {
            model.addAttribute("article", article.get());
        } else {
            return throwNotFoundException(id);
        }
        return "article/create";
    }

    private String throwNotFoundException(@PathVariable String id) {
        throw new NotFoundException("Article Not Found for " + id);
    }

    @PostMapping("/show/delete/{id}")
    public String deletePost(@PathVariable String id, RedirectAttributes attributes) {
        articleService.deleteById(id);

        attributes.addFlashAttribute("message", "Article with id " + id + " deleted successfully!");
        attributes.addFlashAttribute("articles",
                articleService.getAll(PageRequest.of(0, 10)));

        return "redirect:/article";
    }

    @PostMapping
    public String savePost(@AuthenticationPrincipal UserDetails userDetails,
                           Article article, RedirectAttributes attributes) {
        if(article.getId() == null || article.getId().isEmpty()) {
            String id = UUID.randomUUID().toString();
            User user = userService.findByUserName(userDetails.getUsername());
            article.setAuthor(user);
            article.setId(id);
            article.setLink(id);
        } else {
            Optional<Article> optionalArticle = articleService.findById(article.getId());
            optionalArticle.ifPresent(value -> {
                article.setAuthor(value.getAuthor());
                article.setLink(value.getId());
            });
        }
        articleService.save(article);
        attributes.addFlashAttribute("message",
                String.format("Article with id { %s } successfully edited", article.getId()));
        return "redirect:/article/show/" + article.getLink();
    }

}
