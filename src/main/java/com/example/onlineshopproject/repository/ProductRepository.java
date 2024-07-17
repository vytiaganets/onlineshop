package com.example.onlineshopproject.repository;

import com.example.onlineshopproject.entity.ProductEntity;
import com.example.onlineshopproject.repository.customs.ProductCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>, ProductCustomRepository {
    @Query(value =
            "SELECT p.ProductID as productId, p.Name as name, SUM(Quantity) as count, " +
                    "SUM(Quantity*Price) as sum " +
                    "FROM Products p " +
                    "JOIN OrderItems oi ON p.ProductID = oi.ProductID " +
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
            "WHERE (?1 OR CategoryID = ?2) " +
            "AND (?5 OR DiscountPrice IS NOT NULL) " +
            "ORDER BY ?6 ASC ",
            nativeQuery = true)
    List<ProductEntity> findByFilter(Boolean isCategory, Long category, BigDecimal minPrice, BigDecimal maxPrice, Boolean isDiscount, String sort);

//    @Query(value =
//            "SELECT product FROM Product product " +
//                    "WHERE (:isCategory = TRUE OR product.category.categoryId = :category) " +
//                    "AND product.price BETWEEN :minPrice and :maxPrice "+
//                    "AND (:isDiscount = TRUE OR product.discountPrice IS NOT NULL) ",
//            nativeQuery = true)
//    List<ProductEntity> findByFilter(Boolean isCategory, Long category, BigDecimal minPrice,
//                                             BigDecimal maxPrice, Boolean isDiscount, String sort);

//    @Query(value = "SELECT ProductID," +
//            "       Name," +
//            "       Description," +
//            "       Price," +
//            "       DiscountPrice," +
//            "       CategoryID," +
//            "       ImageURL," +
//            "       CreatedAt," +
//            "       UpdatedAt," +
//            "FROM Products as product " +
//            "WHERE (:isCategory = TRUE OR product.CategoryId = :category) " +
//            "  AND product.Price BETWEEN :minPrice and :maxPrice " +
//            "  AND (:isDiscount = TRUE OR product.DiscountPrice IS NOT NULL) ",
//    nativeQuery = true)
//    List<ProductEntity> findByFilter(Boolean isCategory, Long category, BigDecimal minPrice, BigDecimal maxPrice,
//                                     Boolean isDiscount, String sort);

    @Query(value =
    "SELECT p.ProductID as ProductId, p.Name as name, SUM(oi.Quantity) as count, o.CreatedAt " +
    "FROM Products p JOIN OrderItems oi ON p.ProductID = oi.ProductID "+
    "JOIN Orders o ON oi.OrderId = o.OrderID " +
            "WHERE (o.Status = 'PENDING_PAYMENT' and (o.CreatedAt > (Now() -INTERVAL :days DAY))) "+
            "GROUP BY p.ProductID, o.CreatedAt "+
            "Order by p.ProductID ",
            nativeQuery = true)
            List<String> findProductsPending(Integer days);

    @Query(value =
           "SELECT CASE "+
            "WHEN :period = 'MONTH' THEN DATE_FORMAT(o.CreatedAt, '%Y-%m') "+
            "WHEN :period = 'WEEK' THEN DATE_FORMAT(o.CreatedAt, '%Y-%u') "+
            "ELSE DATE_FORMAT(o.CreatedAt, '%Y-%m-%d') "+
            "END as Period, SUM(oi.Quantity * p.Price) as Sum " +
            "FROM Products p "+
            "JOIN OrderItems oi ON p.ProductID = oi.ProductID " +
            "JOIN Orders o ON oi.OrderId = o.OrderID " +
            "WHERE o.Status = 'PENDING_PAYMENT' AND o.CreatedAt >= "+
            "CASE " +
            "WHEN :period = 'MONTH' THEN NOW() - INTERVAL :value MONTH " +
            "WHEN :period = 'WEEK' THEN NOW() - INTERVAL :value WEEK " +
            "ELSE NOW() - INTERVAL :value DAY " +
            "END " +
            "GROUP BY CASE "+
            "WHEN :period = 'MONTH' THEN DATE_FORMAT(o.CreatedAt, '%Y-%m') "+
            "WHEN :period = 'WEEK' THEN DATE_FORMAT(o.CreatedAt, '%Y-%u') "+
            "ELSE DATE_FORMAT(o.CreatedAt, '%Y-%m-%d') "+
            "END ",
            nativeQuery = true
    )
List<String> findProductsProfitByPeriod(String period, Integer value);
}