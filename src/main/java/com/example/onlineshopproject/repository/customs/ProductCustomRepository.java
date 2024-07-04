package com.example.onlineshopproject.repository.customs;

import com.example.onlineshopproject.entity.CategoryEntity;
import com.example.onlineshopproject.entity.ProductEntity;

import java.util.List;

public interface ProductCustomRepository {
    List<ProductEntity> findProductByFilter(CategoryEntity categoryEntity, Double minPrice, Double maxPrice,
                                            Boolean isDiscount, String sort);
}
