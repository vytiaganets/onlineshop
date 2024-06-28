package com.example.onlineshopproject.repository;

import com.example.onlineshopproject.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    //CategoryEntity findByName(String name);
   // Optional<CategoryEntity> findByName(String name);
}