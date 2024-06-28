package com.example.onlineshopproject.security;

import com.example.onlineshopproject.entity.UserEntity;
import com.example.onlineshopproject.exceptions.UserNotFoundException;
import com.example.onlineshopproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserEntity userEntity = userService.getByLogin(username);
            return new User(userEntity.getName(), userEntity.getPassword(),
                    List.of(new SimpleGrantedAuthority(userEntity.getRole().getRoleName())));
        } catch (UserNotFoundException exception) {
            throw new UsernameNotFoundException(exception.getMessage());
        }
    }
}
