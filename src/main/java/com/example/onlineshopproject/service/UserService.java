package com.example.onlineshopproject.service;

import com.example.onlineshopproject.configuration.MapperConfiguration;
import com.example.onlineshopproject.dto.CartResponseDto;
import com.example.onlineshopproject.dto.UserRequestDto;
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

    public UserRequestDto registerUser(UserRequestDto userRequestDto) {
        return null;
    }

    public UserEntity getByLogin(String username) {
        return null;
    }

    public List<UserRequestDto> getUser() {
        List<UserEntity> userEntityList = new ArrayList<>();
        userRepository.findAll().forEach(userEntityList::add);
        List<UserRequestDto> userRequestDtoList = MapperConfiguration.convertList(userEntityList,
                mappers::convertToUserResponseDto);
        return userRequestDtoList;
    }

    public UserRequestDto getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Не найден пользователь с таким Id"));
        UserRequestDto userRequestDto = mappers.convertToUserResponseDto(userEntity);
        return userRequestDto;
    }

    public UserRequestDto updateUser(UserRequestDto userRequestDto) throws FileNotFoundException {
        if (userRequestDto.getUserId() != null) {
            Optional<UserEntity> userEntityOptional = userRepository.findById(userRequestDto.getUserId());
            if (userEntityOptional.isPresent()) {
                UserEntity userEntity = userEntityOptional.get();
                userEntity.setUserId((userRequestDto.getUserId() != null) ? userRequestDto.getUserId() : userEntity.getUserId());
                userEntity.setName((userRequestDto.getName() != null) ? userRequestDto.getName() : userEntity.getName());
                userEntity.setEmail((userRequestDto.getEmail() != null) ? userRequestDto.getEmail() : userEntity.getEmail());
                userEntity.setPhoneNumber((userRequestDto.getPhoneNumber() != null) ? userRequestDto.getPhoneNumber() :
                        userEntity.getPhoneNumber());
                userEntity.setRole((userRequestDto.getRole() != null) ? userRequestDto.getRole() :
                        userEntity.getRole());
                UserEntity userUpdate = userRepository.save(userEntity);
                return mappers.convertToUserResponseDto(userUpdate);
            } else {

                log.error("Не найден пользователь с Id " + userRequestDto.getUserId());
                throw new NotFoundInDbException("Не найден пользователь с Id " + userRequestDto.getUserId());
            }
        } else {
            throw new FileNotFoundException("Не корректный параметр userDto");
        }
    }

    public UserRequestDto getByEmail(String email) {
        List<UserEntity> userEntityList = userRepository.getByEmail(email);
        UserRequestDto userRequestDto = null;
        if (userEntityList != null && !userEntityList.isEmpty()) {
            //userDto = mappers.convertToUserDto(userEntityList.getFirst());
            userRequestDto = mappers.convertToUserResponseDto(userEntityList.get(0));
        } else {
            new NotFoundInDbException("Не найден в БД пользователь с e-mail: " + email);
        }
        return userRequestDto;

    }

    public UserRequestDto createUser(UserRequestDto userCredentialsDto) {
        userCredentialsDto.setUserId(null);
        UserEntity userEntity = mappers.convertToUserEntity(userCredentialsDto);
        userEntity.setPasswordHash(passwordEncoder.encode(userCredentialsDto.getPasswordHash()));
        CartResponseDto cartResponseDto = userCredentialsDto.getCartResponseDto();
        CartEntity cartEntity = null;
        if (cartResponseDto != null) {
            cartEntity = cartRepository.findById(cartResponseDto.getCartId())
                    .orElseThrow(() -> new NotFoundInDbException("Не найдена в БД корзина с id " + cartResponseDto.getCartId()));
        }
        UserEntity userEntityResponce = userRepository.save(userEntity);

        return mappers.convertToUserResponseDto(userEntityResponce);
    }
}