package com.example.onlineshopproject.query;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "ProductProfit")
@AllArgsConstructor
@NoArgsConstructor
public class ProductProfit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private Long productId;
    @Column(name = "Period")
    private Timestamp period;
    @Column(name = "Sum")
    private BigDecimal sum;
}
