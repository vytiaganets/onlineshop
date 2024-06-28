package com.example.onlineshopproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CartItems")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItemEntity {
    @Id
    @Column(name = "CartItemID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CartID")
    private CartEntity cartEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductID")
    private ProductEntity productEntity;

    @Column(name = "Quantity")
    private int quantity;
}
