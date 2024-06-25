package com.example.onlineshop.mapper;

import org.mapstruct.Mapper;
import com.example.onlineshop.dto.UserCreateDto;
import com.example.onlineshop.dto.UserDto;
import com.example.onlineshop.entity.UserEntity;

@Mapper(componentModel =  "spring")
public interface UserMapper {
    UserDto toDto(UserEntity userEntity);
    UserEntity toEntity(UserDto userDto);
    UserEntity userCreateDtoToEntity(UserCreateDto userCreateDto);
}