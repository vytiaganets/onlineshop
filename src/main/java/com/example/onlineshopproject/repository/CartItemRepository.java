package com.example.onlineshopproject.repository;

import com.example.onlineshopproject.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
}
