package com.example.onlineshopproject.repository;

import com.example.onlineshopproject.entity.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {
}
