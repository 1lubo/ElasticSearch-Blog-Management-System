package com.example.bloggestx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
public class AuthController {

    @GetMapping("/login")
    String login(@RequestParam(required = false, value = "error") Optional<Integer> status, Model model) {
        if(status.isPresent()){
            model.addAttribute("message", "Incorrect username or password.");
        }
        return "common/login";
    }

}
