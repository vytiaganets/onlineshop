package com.example.onlineshopproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private Long productId;

    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "Price")
    private BigDecimal price;

    @Column(name = "imageURL")
    private String imageUrl;

    @Column(name = "DiscountPrice")
    private BigDecimal discountPrice;

    @Column(name = "CreatedAt")
    private Timestamp createdAt;

    @Column(name = "UpdatedAt")
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryID")
    private CategoryEntity categoryEntity;

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private Set<CartItemEntity> cartItemEntitySet = new HashSet<>();

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private Set<OrderItemEntity> orderItemEntitySet = new HashSet<>();

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private Set<FavoriteEntity> favoriteEntitySet = new HashSet<>();
}
