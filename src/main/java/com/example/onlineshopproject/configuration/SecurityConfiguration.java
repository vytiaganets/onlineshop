package com.example.onlineshopproject.configuration;

import com.example.onlineshopproject.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    //AV Endpoint set
    private static final String[] SWAGGER = {
            "v2/api-docs",
            "v3/api-docs",
            "/swagger-resources/**",
            "/swagger-resources",
            "/swagger-ui/",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/swagger-resources/configuration/ui",
            "/swagger-resources/configuration/security"
    };
    //AV Authorization check filter by token
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests
                        (request -> request
                                //AB is allowed for the swagger
                                .requestMatchers(SWAGGER).permitAll()
                                //AB allow the user to log in to receive a token
                                .requestMatchers("/users/login", "/users/token").permitAll()
                                //AB allows registering a new user without a token and passwords
                                .requestMatchers("/users/register").permitAll()
                                .anyRequest().permitAll())
                                //.authenticated())
                //Question http://localhost:8080/swagger-ui/index.html Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long';
                // For input string: "login"]
                //AB basic authentication
                .httpBasic(Customizer.withDefaults())
                //AB disable state storage between requests
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //AB work with token
                .addFilterBefore(jwtFilter,
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