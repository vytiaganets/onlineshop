package com.example.onlineshopproject.security;

import com.example.onlineshopproject.security.model.JwtAuthResponce;
import com.example.onlineshopproject.security.model.SignInRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtService jwtService;
    @Override
    public JwtAuthResponce authenticate(SignInRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails user = userDetailsService.loadUserByUsername(request.getEmail()
        );
        String token = jwtService.generateToken(user);
        return new JwtAuthResponce(token);
    }
}
