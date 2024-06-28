package com.example.onlineshopproject.repository;

import com.example.onlineshopproject.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{

        Optional<UserEntity> findById(long id);

        void deleteById(Long id);

        Optional<UserEntity> findByName(String name);
}
