package com.example.onlineshopproject.entity;

import com.example.onlineshopproject.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Table(name = "Orders")
@Entity
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderId")
    private Long orderId;
    private String deliveryAddress;
    private String contactPhone;
    private String deliveryMethod;
    private Status status;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId", nullable = false)
    private UserEntity userEntity;

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL)
    private List<OrderItemEntity> items = new ArrayList<>();

}
