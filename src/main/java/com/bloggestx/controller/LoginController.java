package com.bloggestx.controller;

import com.bloggestx.dto.request.SigninRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;


@Controller
@RequestMapping("/login")
public class LoginController {
    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @GetMapping()
    String login(@RequestParam(required = false, value = "error") Optional<Boolean> error, Model model) {
        if(error.isPresent()){
            model.addAttribute("message", "Incorrect username or password.");
        }
        return "common/login";
    }

    @PostMapping()
    void login(@RequestBody SigninRequest signinRequest, HttpServletRequest request) {
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(
                signinRequest.getUsername(),signinRequest.getPassword());
        Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authenticationResponse);

        HttpSession session = request.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, securityContext);
    }

}
