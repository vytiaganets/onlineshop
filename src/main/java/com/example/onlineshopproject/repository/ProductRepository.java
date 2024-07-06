package com.example.onlineshopproject.repository;

import com.example.onlineshopproject.entity.ProductEntity;
import com.example.onlineshopproject.repository.customs.ProductCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>, ProductCustomRepository {
    @Query(value =
            "SELECT p.ProductID as productId, p.Name as name, SUM(Quantuty) as count, SUM(Quantity*Price) as sum " +
                    "FROM Products p JOIN " +
                    "OrderItems oi ON p.ProductID = oi.ProductID " +
                    "JOIN Orders o ON oi.OrderId = o.OrderID " +
                    "WHERE o.Status = ?1 " +
                    "GROUP BY p.ProductID, p.Name " +
                    "ORDER BY Count DESC " +
                    "LIMIT 10",
            nativeQuery = true)
    List<String> findTop10Products(String status);

    //AV interval dat
    //https://stackoverflow.com/questions/72978636/how-to-do-decimal-precision-in-spring-data-jpa-query-annotation
    @Query(value = "SELECT ProductID, Name, Description, Price, DiscountPrice, CategoryID,ImageURL, CreatedAt, " +
            "UpdatedAt FROM Products " +
            "WHERE (?1 OR CategooryID = ?2) " +
            "AND (?5 OR DiscountPrice IS NOT NULL) " +
            "ORDER BY ?6 ASC ",
            nativeQuery = true)
    List<ProductEntity> findProductByFilter(Boolean isCategory, Long category, Double moinPrice, Double maxPrice,
                                            Boolean isDiscount, String sort);
}
