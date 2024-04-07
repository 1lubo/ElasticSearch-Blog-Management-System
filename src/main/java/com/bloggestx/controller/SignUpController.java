package com.bloggestx.controller;

import com.bloggestx.dto.request.SignUpRequest;
import com.bloggestx.exception.UsernameAlreadyTakenException;
import com.bloggestx.model.User;
import com.bloggestx.model.enums.Role;
import com.bloggestx.model.enums.RoleDescription;
import com.bloggestx.service.BlogUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Controller
@RequestMapping("/signup")
public class SignUpController {
    private final BlogUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    public SignUpController(BlogUserDetailsService userDetailsService, AuthenticationManager authenticationManager,
                            PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    String signup(){
        return "common/signup";
    }
    @PostMapping
    void signup(SignUpRequest signUpRequest, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        User user = userDetailsService.findByUserName(signUpRequest.getUsername());

        if( user != null ){
            throw new UsernameAlreadyTakenException("Username is already taken!");
        }

        createUserFromSignUpRequest(signUpRequest);

        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(
                signUpRequest.getUsername(), signUpRequest.getPassword());
        Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);

        SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
                .getContextHolderStrategy();

        SecurityContext securityContext = securityContextHolderStrategy.createEmptyContext();
        securityContext.setAuthentication(authenticationResponse);

        HttpSession session = request.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, securityContext);


        redirectStrategy.sendRedirect(request, response, "/article?signin=true");

    }

    private void createUserFromSignUpRequest(SignUpRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        user.setDescription(RoleDescription.USER);
        userDetailsService.save(user);
    }
}
