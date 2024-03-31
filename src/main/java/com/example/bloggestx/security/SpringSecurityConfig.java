package com.example.bloggestx.security;

import com.example.bloggestx.model.User;
import com.example.bloggestx.model.enums.Role;
import com.example.bloggestx.model.enums.RoleDescription;
import com.example.bloggestx.service.BlogUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import java.util.UUID;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private final BlogUserDetailsService userService;
    @Autowired
    public SpringSecurityConfig(BlogUserDetailsService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests.requestMatchers("/article", "/article/show/**"
                        , "/webjars/**", "/css/**", "/favicon.ico", "/index", "/login**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/article", "/article/delete/**").authenticated()
                        .requestMatchers("/article/edit/**", "/article/new").authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/article?status=200")
                        .failureUrl("/login?error=true")
                )
                .logout((logout) -> logout
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/article?status=202"))
                .securityContext((securityContext) -> securityContext
                        .securityContextRepository(securityContextRepository()));
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityContextRepository securityContextRepository(){
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        );
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            userService.deleteAll();
            userService.save(new User(UUID.randomUUID().toString(), "user",
                    passwordEncoder().encode("password"), Role.USER, RoleDescription.USER));
            userService.save(new User(UUID.randomUUID().toString(), "admin",
                    passwordEncoder().encode("password"), Role.ADMIN, RoleDescription.ADMIN));
        };
    }
}
