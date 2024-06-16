package com.bloggestx.controller;

import com.bloggestx.model.Article;
import com.bloggestx.model.User;
import com.bloggestx.service.BlogUserDetailsService;
import com.bloggestx.service.implementation.ArticleServiceImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/article")
public class ArticleController {
    private final ArticleServiceImpl articleService;
    private final BlogUserDetailsService userService;

    public ArticleController(ArticleServiceImpl articleService, BlogUserDetailsService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model,
                        @AuthenticationPrincipal UserDetails userDetails,
                        @RequestParam(required = false, value = "signin") Optional<Boolean> signin,
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

        if (signin.isPresent()) {
            if (signin.get()) {
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
        Article article = articleService.getArticlebyLink(link);
        model.addAttribute("article", article);
        return "article/show";
    }

    @GetMapping("/new")
    public String newPost(){
        return "article/create";
    }

    @GetMapping("/show/edit/{link}")
    public String editPost(@PathVariable String link, Model model){
        Article article = articleService.getArticlebyLink(link);
        model.addAttribute("article", article);
        return "article/create";
    }

    @PostMapping("/show/delete/{id}")
    public String deletePost(@PathVariable String id, RedirectAttributes attributes) {

        articleService.deleteArticle(id);

        attributes.addFlashAttribute("message", "Article with id " + id + " deleted successfully!");
        attributes.addFlashAttribute("articles",
                articleService.getAll(PageRequest.of(0, 10)));

        return "redirect:/article";
    }

    @PostMapping
    public String savePost(@AuthenticationPrincipal UserDetails userDetails,
                           Article article, RedirectAttributes attributes) {
        if(article.getId() == null || article.getId().isEmpty()) {
            User user = userService.findByUserName(userDetails.getUsername());
            article.setAuthor(user);
        } else {
            Article existingArticle = articleService.getArticlebyId(article.getId());
            article.setAuthor(existingArticle.getAuthor());
        }

        articleService.saveArticle(article);

        attributes.addFlashAttribute("message",
                String.format("Article with id { %s } saved successfully", article.getId()));
        return "redirect:/article/show/" + article.getLink();
    }

}
