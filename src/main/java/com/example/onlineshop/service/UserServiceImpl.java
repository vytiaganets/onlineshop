package com.example.onlineshop.service;

import com.example.onlineshop.dto.UserCreateDto;
import com.example.onlineshop.entity.UserEntity;
import com.example.onlineshop.exceptions.UserNotFoundException;
import com.example.onlineshop.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Value("1")
    private Long defaultUserId;

    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserEntity> getAll() {
        log.debug("Получение всех пользователей.");
        return userJpaRepository.findAll();
    }

    @Override
    public UserEntity findById(long id) {
        log.debug("Попытка найти пользователя по идентификатору: {}", id);
        return userJpaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Пользователь не найден с идентификатором: {}", id);
                    return new UserNotFoundException("Нет пользователей с идентификатором " + id);
                });
    }

    @Override
    public UserEntity create(UserEntity userEntity) {
        log.debug("Попытка создать пользователя: {}", userEntity.getEmail());
        String password = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(password);
        return userJpaRepository.save(userEntity);
    }
    @Override
    public UserEntity edit(long id, UserCreateDto userCreateDto) {
        log.debug("Попытка отредактировать пользователя с идентификатором: {}", id);
        return userJpaRepository.findById(id).map(user -> {
            user.setName(userCreateDto.getName());
            user.setPhone(userCreateDto.getPhone());
            return userJpaRepository.save(user);
        }).orElseThrow(() -> {
            log.error("Пользователь с идентификатором {} не найден для редактирования", id);
            return new UserNotFoundException("Пользователь с идентификатором " + id + "не найден.");
        });
    }

    @Override
    public void delete(Long id) {
        log.debug("Попытка удалить пользователя с идентификатором: {}", id);
        UserEntity user = userJpaRepository.findById(id).orElseThrow(() -> {
            log.error("Попытка удалить несуществующего пользователя с идентификатором: {}", id);
            return new UserNotFoundException("Нет пользователей с идентификатором " + id);
        });
        userJpaRepository.deleteById(id);
    }

    @Override
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            throw new SecurityException("Пользователь не аутентифицирован.");
        }
        Object principal = authentication.getPrincipal();
        if(principal instanceof UserDetails){
            String username = ((UserDetails) principal).getUsername();
            UserEntity user = getByLogin(username);
            return user.getId();
        }else {
            throw new IllegalArgumentException("Основной объект аутентификации не может быть использован для " +
                    "получения идентификатора.");
        }
    }

    @Override
    public UserEntity getByLogin(String login) {
        return userJpaRepository.findByName(login).orElseThrow(() -> new UserNotFoundException("Пользователь с " +
                "логином {} " +  login + " не найден." ));
    }
}
