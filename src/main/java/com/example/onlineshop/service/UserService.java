package com.example.onlineshop.service;

import com.example.onlineshop.dto.UserCreateDto;
import com.example.onlineshop.entity.UserEntity;
import org.mapstruct.control.MappingControl;

import java.util.List;

public interface UserService {
    List<UserEntity> getAll();
    UserEntity findById(long id);
    UserEntity create(UserEntity userEntity);
    UserEntity getByLogin(String login);

    UserEntity edit(long id, UserCreateDto  userCreateDto);

    void delete(Long id);
    Long getCurrentUserId();
}
