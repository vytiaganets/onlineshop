package com.example.onlineshopproject.repository;

import com.example.onlineshopproject.entity.OrderEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
//    @Modifying(clearAutomatically=true, flushAutomatically=true)
//    @Transactional
//    @Query("UPDATE Order o SET o.status = "
//            "CASE " +
//            "WHEN o.status = 'CREATED' THEN 'PENDING_PAYMENT'" +
//            "WHEN o.status = 'PENDING_PAYMENT' THEN 'PAID'" +
//            "WHEN o.status = 'PAID' THEN 'ON_THE_WAY' " +
//            "WHEN o.status = 'ON_THE_WAY' THEN 'DELIVERED' " +
//            "ELSE o.status END, " +
//            "o.updatedAt = CURRENT_TIMESTAMP " +
//            "WHERE o.createdAt > :startDate " +
//    Integer UpdateOrderEntityStatuses(Timestamp startDate, @Param("statuses") List<Status
//    @Query ("SELECT COUNT (o) FROM Order o WHERE o.status != 'DELIVERED' AND o.status
//            Integer countUndeliveredOrders(Timestamp startDate);
}
