package com.example.onlineshopproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Table(name = "OrdeerItems")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderItemID")
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId", nullable = false)
    private OrderEntity orderEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductId", nullable = false)
    private ProductEntity productEntity;

    private int quantity;
    private BigDecimal priceAtPurchase;
}
