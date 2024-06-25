package com.example.onlineshop.entity;

import jakarta.persistence.*;
import lombok.*;
import com.example.onlineshop.enums.UserRole;
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String phone;
    private String password;
    @Enumerated(EnumType.ORDINAL)
    private UserRole role;

}
