package com.example.onlineshopproject.entity;

import com.example.onlineshopproject.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Long userId;
    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "Name", nullable = false)
    private String name;

    @NotNull
    //@Email
    @Column(name = "Email", unique = true, nullable = false)
    private String email;

    //@Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Номер телефона недействителен.")
    @Column(name = "PhoneNumber")
    private String phoneNumber;

    @NotNull
    @Size(min = 8, message = "Пароль должен содержать не менее 8 символов")
    @Column(name = "PasswordHash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "Role", nullable = false)
    private UserRole role;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "userEntity", cascade = CascadeType.ALL)
    private CartEntity cartEntity;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private Set<FavoriteEntity> favoriteEntitySet = new HashSet<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private Set<OrderEntity> orderEntitySet = new HashSet<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HistoryEntity> historyEntityList;

}