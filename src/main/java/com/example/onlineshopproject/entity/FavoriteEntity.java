package com.example.onlineshopproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Favorities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteEntity {
    @Id@Column(name = "FavoritiesID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long favoriteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false)
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductID", nullable = false)
    private ProductEntity productEntity;

}
