package com.example.onlineshopproject.service;

import com.example.onlineshopproject.configuration.MapperConfiguration;
import com.example.onlineshopproject.dto.CartDto;
import com.example.onlineshopproject.dto.UserDto;
import com.example.onlineshopproject.entity.CartEntity;
import com.example.onlineshopproject.entity.UserEntity;
import com.example.onlineshopproject.exceptions.NotFoundInDbException;
import com.example.onlineshopproject.mapper.Mappers;
import com.example.onlineshopproject.repository.CartRepository;
import com.example.onlineshopproject.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final Mappers mappers;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDto registerUser(UserDto userDto) {
        return null;
    }

    public UserEntity getByLogin(String username) {
        return null;
    }

    public List<UserDto> getUser() {
        List<UserEntity> userEntityList = new ArrayList<>();
        userRepository.findAll().forEach(userEntityList::add);
        List<UserDto> userDtoList = MapperConfiguration.convertList(userEntityList, mappers::convertToUserDto);
        return userDtoList;
    }

    public UserDto getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Не найден пользователь с таким Id"));
        UserDto userDto = mappers.convertToUserDto(userEntity);
        return userDto;
    }

    public UserDto updateUser(UserDto userDto) throws FileNotFoundException {
        if (userDto.getUserId() != null) {
            Optional<UserEntity> userEntityOptional = userRepository.findById(userDto.getUserId());
            if (userEntityOptional.isPresent()) {
                UserEntity userEntity = userEntityOptional.get();
                userEntity.setUserId((userDto.getUserId() != null) ? userDto.getUserId() : userEntity.getUserId());
                userEntity.setName((userDto.getName() != null) ? userDto.getName() : userEntity.getName());
                userEntity.setEmail((userDto.getEmail() != null) ? userDto.getEmail() : userEntity.getEmail());
                userEntity.setPhoneNumber((userDto.getPhoneNumber() != null) ? userDto.getPhoneNumber() :
                        userEntity.getPhoneNumber());
                userEntity.setRole((userDto.getRole() != null) ? userDto.getRole() :
                        userEntity.getRole());
                UserEntity userUpdate = userRepository.save(userEntity);
                return mappers.convertToUserDto(userUpdate);
            } else {

                log.error("Не найден пользователь с Id " + userDto.getUserId());
                throw new NotFoundInDbException("Не найден пользователь с Id " + userDto.getUserId());
            }
        } else {
            throw new FileNotFoundException("Не корректный параметр userDto");
        }
    }

    public UserDto getByEmail(String email) {
//        List<UserEntity> userEntityList = userRepository.getByEmail(email);
//        UserDto userDto = null;
//        if (userEntityList != null && !userEntityList.isEmpty()) {
//            userDto = mappers.convertToUserDto(userEntityList.getFirst());
//        } else {
//            new NotFoundInDbException("Не найден в БД пользователь с e-mail: " + email);
//        }
//        return userDto;
        return null;
    }

    public UserDto createUser(UserDto userCredentialsDto) {
        userCredentialsDto.setUserId(null);
        UserEntity userEntity = mappers.convertToUserEntity(userCredentialsDto);
        userEntity.setPasswordHash(passwordEncoder.encode(userCredentialsDto.getPasswordHash()));
        CartDto cartDto = userCredentialsDto.getCartDto();
        CartEntity cartEntity = null;
        if (cartDto != null) {
            cartEntity = cartRepository.findById(cartDto.getCartId())
                    .orElseThrow(() -> new NotFoundInDbException("Не найдена в БД корзина с id " + cartDto.getCartId()));
        }
        UserEntity userEntityResponce = userRepository.save(userEntity);

        return mappers.convertToUserDto(userEntityResponce);
    }
}