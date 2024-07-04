package com.example.onlineshopproject.query;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ProductCount")
@AllArgsConstructor
@NoArgsConstructor
public class ProductCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private Long productId;
    @Column(name = "Name")
    private String name;
    @Column(name = "Count")
    private Long count;
    @Column(name = "Sum")
    private Double sum;
}
