package com.example.onlineshopproject.configuration;

import com.example.onlineshopproject.security.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    //AV Endpoint set
    private static final String[] URLS = {
            "/swagger-ui.html",
            "/manage/**",
            "/api/v1/auth/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "v2/api-docs",
            "v3/api-docs",
            "/swagger-resources/**",
            "/swagger-resources",
            "/swagger-ui/",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/manage/**",
            "/swagger-resources/configuration/ui",
            "/swagger-resources/configuration/security",
            "/users/login",
            "/users/token",
            "/users/register"
    };
    //AV Authorization check filter by token
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                //AB disable state storage between requests
                .sessionManagement(session -> session
                        .sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests
                        (request -> request
                                //AB is allowed for the swagger
                                .requestMatchers(URLS).permitAll()
                                //AB allow the user to log in to receive a token
                                //AB allows registering a new user without a token and passwords
                                .anyRequest()//.permitAll())
                                .authenticated())
                //AB basic authentication
                //AB work with token
                .addFilterAfter(jwtFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    //AV Bin for password encryption
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}