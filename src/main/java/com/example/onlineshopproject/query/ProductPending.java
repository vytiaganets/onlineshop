package com.example.onlineshopproject.query;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "ProductPending")
@AllArgsConstructor
@NoArgsConstructor
public class ProductPending {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private Long productId;
    @Column(name = "Name")
    private String name;
    @Column(name = "Count")
    private Integer count;
    @Column(name = "CreatedAt")
    private Timestamp createAt;
}
