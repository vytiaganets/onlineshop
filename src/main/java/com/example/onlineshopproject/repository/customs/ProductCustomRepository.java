package com.example.onlineshopproject.repository.customs;

import com.example.onlineshopproject.entity.CategoryEntity;
import com.example.onlineshopproject.entity.ProductEntity;

import java.math.BigDecimal;
import java.util.List;

public interface ProductCustomRepository {
    List<ProductEntity> findProductByFilter(CategoryEntity categoryEntity, BigDecimal minPrice, BigDecimal maxPrice,
                                            Boolean isDiscount, String sort);
}
