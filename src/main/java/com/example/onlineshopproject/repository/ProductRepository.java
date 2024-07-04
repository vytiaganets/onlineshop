package com.example.onlineshopproject.repository;

import com.example.onlineshopproject.entity.ProductEntity;
import com.example.onlineshopproject.repository.customs.ProductCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>, ProductCustomRepository {
    @Query(value = "" +
            "SELECT p.ProductID, p.Name, SSUM(Quantuty) Count, SUM(Quantity*Price) Sum " + "FROM Products p JOIN " +
            "OrderItems oi ON p.ProductID = oi.ProductID " +
            "WHERE o.Status=?1 " +
            "GROUP BY p.ProductID, p.Name " +
            "ORDER BY Count DESC " +
            "LIMIT 10",
            nativeQuery = true)
    List findTop10Products(String status);

    //AV interval dat
    //https://stackoverflow.com/questions/72978636/how-to-do-decimal-precision-in-spring-data-jpa-query-annotation
}
