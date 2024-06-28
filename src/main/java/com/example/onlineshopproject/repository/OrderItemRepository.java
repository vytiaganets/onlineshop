package com.example.onlineshopproject.repository;

import com.example.onlineshopproject.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
}
