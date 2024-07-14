package com.example.onlineshopproject.service;

import com.example.onlineshopproject.configuration.MapperConfiguration;
import com.example.onlineshopproject.dto.CartResponseDto;
import com.example.onlineshopproject.dto.UserRequestDto;
import com.example.onlineshopproject.entity.CartEntity;
import com.example.onlineshopproject.entity.UserEntity;
import com.example.onlineshopproject.enums.UserRole;
import com.example.onlineshopproject.exceptions.NotFoundInDbException;
import com.example.onlineshopproject.exceptions.UserInvalidArgumentException;
import com.example.onlineshopproject.exceptions.UserNotFoundException;
import com.example.onlineshopproject.mapper.Mappers;
import com.example.onlineshopproject.repository.CartRepository;
import com.example.onlineshopproject.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final Mappers mappers;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public void register(UserRequestDto userRequestDto) {
        log.info("Attempting to registration a user:{}", userRequestDto.getEmail());
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            log.error("User with email already exists: {}", userRequestDto.getEmail());
            throw new UserInvalidArgumentException("User already exists.");
        }
        UserEntity userEntity = mappers.convertToUserEntity(userRequestDto);
        userEntity.setRole(UserRole.USER);
        CartEntity cartEntity = new CartEntity();
        cartEntity.setUserEntity(userEntity);
        cartRepository.save(cartEntity);
        userEntity.setCartEntity(cartEntity);
        userRepository.save(userEntity);
    }

    public List<UserRequestDto> getAll() {
        log.debug("Fetching all users.");
        List<UserEntity> userEntityList = new ArrayList<>();
        userRepository.findAll().forEach(userEntityList::add);
        List<UserRequestDto> userRequestDtoList = MapperConfiguration.convertList(userEntityList,
                mappers::convertToUserResponseDto);
        return userRequestDtoList;
    }

    public UserRequestDto getById(Long userId) {
        log.debug("Attempting to find a user by id: {}", userId);
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() ->{
                    log.error("User not found with id: {}", userId);
                   return new EntityNotFoundException("User with id not found");
                });
        UserRequestDto userRequestDto = mappers.convertToUserResponseDto(userEntity);
        return userRequestDto;
    }

    public UserRequestDto update(UserRequestDto userRequestDto) throws FileNotFoundException {
        log.debug("Attempting to update user with id: {}", userRequestDto.getUserId());
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
                log.info("User with id: {} successfully updated.");
                return mappers.convertToUserResponseDto(userUpdate);
            } else {
                log.error("User with id {} not found." + userRequestDto.getUserId());
                throw new UserNotFoundException("User with id " + userRequestDto.getUserId() + " not found.");
            }
        } else {
            log.error("User not found with id: {}", userRequestDto.getUserId());
            throw new UserInvalidArgumentException("Invalid userRequestDto parameter.");
        }
    }

    public UserRequestDto getByEmail(String email) {
        log.debug("Attempting to find a user by email: {}", email);
        List<UserEntity> userEntityList = userRepository.getByEmail(email);
        UserRequestDto userRequestDto = null;
        if (userEntityList != null && !userEntityList.isEmpty()) {
            userRequestDto = mappers.convertToUserResponseDto(userEntityList.get(0));
        } else {
            log.error("User not found with email: {}", email);
            throw new UserNotFoundException("The user with e-mail was not found in the database:" + email);
        }
        return userRequestDto;
    }

    public UserRequestDto create(UserRequestDto userCredentialsDto) {
        userCredentialsDto.setUserId(null);
        UserEntity userEntity = mappers.convertToUserEntity(userCredentialsDto);
        userEntity.setPasswordHash(passwordEncoder.encode(userCredentialsDto.getPasswordHash()));
        CartResponseDto cartResponseDto = userCredentialsDto.getCartResponseDto();
        CartEntity cartEntity = null;
        if (cartResponseDto != null) {
            cartEntity = cartRepository.findById(cartResponseDto.getCartId())
                    .orElseThrow(() -> {
                        log.error("Cart not found with id: {}", cartResponseDto.getCartId());
                        throw  new UserNotFoundException("Cart with id not found in the database " + cartResponseDto.getCartId());
                    });
        }
        UserEntity userEntityResponse = userRepository.save(userEntity);
        return mappers.convertToUserResponseDto(userEntityResponse);
    }

    public void delete(Long userId) {
        log.debug("Attempting to delete use with id: {}", userId);
        UserEntity userEntity = userRepository
                .findById(userId)
                .orElseThrow(() -> {
                    log.error("Attempted to delete non-existing user with id: {}", userId);
                    throw new UserNotFoundException("User not found");
                });
//        if (userEntity.getCartEntity() != null) {
//            cartRepository.delete(userEntity.getCartEntity());
//        }
        userRepository.delete(userEntity);
    }
}