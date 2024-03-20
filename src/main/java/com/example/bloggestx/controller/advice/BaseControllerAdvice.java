package com.example.bloggestx.controller.advice;

import com.example.bloggestx.model.principal.BlogUserPrincipal;
import com.example.bloggestx.service.BlogUserDetailsService;
import com.example.bloggestx.exception.NotFoundException;
import com.example.bloggestx.model.User;
import freemarker.core.ParseException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Optional;


@ControllerAdvice
public class BaseControllerAdvice {
    private final BlogUserDetailsService userService;

    public BaseControllerAdvice(BlogUserDetailsService userService){
        this.userService = userService;
    }

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException e, Model model) {
        model.addAttribute("status", 400);
        model.addAttribute("exception", e);
        return "common/error";
    }

    @ExceptionHandler(BadCredentialsException.class)
    public void handleBadCredentialsException(Exception e, Model model){
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
        model.addAttribute("pageNumber", page.orElseGet(() -> 0));
    }
}
