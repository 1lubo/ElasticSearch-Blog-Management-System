package com.bloggestx.controller.advice;

import com.bloggestx.exception.UsernameAlreadyTakenException;
import com.bloggestx.model.principal.BlogUserPrincipal;
import com.bloggestx.service.BlogUserDetailsService;
import com.bloggestx.exception.ArticleNotFoundException;
import com.bloggestx.model.User;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Optional;


@ControllerAdvice
public class BaseControllerAdvice {
    private final BlogUserDetailsService userService;

    public BaseControllerAdvice(BlogUserDetailsService userService){
        this.userService = userService;
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public String handleNotFoundException(ArticleNotFoundException e, Model model) {
        model.addAttribute("status", 400);
        model.addAttribute("exception", e);
        return "common/error";
    }

    @ExceptionHandler(UsernameAlreadyTakenException.class)
    public String handleUsernameAlreadyTakenException(UsernameAlreadyTakenException exception, Model model) {
        model.addAttribute("message", exception.getMessage());
        return "common/signup?error=true";
    }

    @ExceptionHandler(BadCredentialsException.class)
    public void handleBadCredentialsException(Model model){
        model.addAttribute("message", "Incorrect Username or Password");
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model){
        model.addAttribute("status", 500);
        model.addAttribute("exception", e);

        return "common/error";
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public String handleInternalServerException(Exception e, Model model){
        model.addAttribute("status", 500);
        model.addAttribute("exception", e);

        return "common/error";
    }

    @ModelAttribute
    public void addCommonAttributes(@AuthenticationPrincipal BlogUserPrincipal userDetails, Model model) {
        if(userDetails != null) {
            User user = userService.findByUserName(userDetails.getUsername());
            model.addAttribute("user", user);
        }
    }

    @ModelAttribute
    public void addPageNumber(@RequestParam(required = false, value = "page") Optional<Integer> page, Model model){
        model.addAttribute("pageNumber", page.orElse(0));
    }
}
