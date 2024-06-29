package com.example.onlineshopproject.query;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ProductCount {
    @Id
    @Column(name = "ProductID")
    private long productId;
    @Column(name = "Name")
    private String name;
    //@Column("Count")
    private Long count;
    //@Column(name = "Sum")
    private Double sum;
}
