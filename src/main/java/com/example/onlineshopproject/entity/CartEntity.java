package com.example.onlineshopproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Cart")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class CartEntity {
    @Id
    @Column(name = "CartID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "cartEntity", cascade = CascadeType.ALL)
    private Set<CartItemEntity> cartItemEntitySet = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId", referencedColumnName = "userId")
    private UserEntity userEntity;

//    public void getCartItemEntitySet(Set<CartItemEntity> cartItemEntitySet) {
//    }
}
