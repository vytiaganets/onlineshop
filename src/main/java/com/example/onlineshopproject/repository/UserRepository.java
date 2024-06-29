package com.example.onlineshopproject.repository;

import com.example.onlineshopproject.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    //@Query("SELECT u FROM Users u WHERE u.email=?1")
    List<UserEntity> getByEmail(String email);
}
