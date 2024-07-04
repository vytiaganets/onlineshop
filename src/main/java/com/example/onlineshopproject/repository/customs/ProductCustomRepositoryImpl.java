package com.example.onlineshopproject.repository.customs;

import com.example.onlineshopproject.entity.CategoryEntity;
import com.example.onlineshopproject.entity.OrderEntity;
import com.example.onlineshopproject.entity.ProductEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;

public class ProductCustomRepositoryImpl implements ProductCustomRepository{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<ProductEntity> findProductByFilter(CategoryEntity categoryEntity, Double minPrice, Double maxPrice,
                                                    Boolean isDiscount, String sort) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductEntity> productEntityCriteriaQuery = criteriaBuilder.createQuery(ProductEntity.class);//SELECT
        Root<ProductEntity> productEntityRoot = productEntityCriteriaQuery.from((ProductEntity.class));//FROM
        //WHERE
        List<Predicate> predicateList = new ArrayList<>();
        if (categoryEntity != null) {
            predicateList.add(criteriaBuilder.equal(productEntityRoot.get("category"), categoryEntity));
            //categoryEntity = 1
        }
        Predicate filterPrice = null;
        if (minPrice != null && maxPrice != null && minPrice < maxPrice) {
            filterPrice = criteriaBuilder.between(productEntityRoot.get("price"), minPrice, maxPrice);
        } else if (minPrice != null) {//>minPrice
            filterPrice = criteriaBuilder.gt(productEntityRoot.get("price"), minPrice);//price>1
        } else if (maxPrice != null) {
            filterPrice = criteriaBuilder.lt(productEntityRoot.get("price"), maxPrice);//price<10
        }
        if (filterPrice != null) {
            predicateList.add(filterPrice);
        }
        if (isDiscount) {
            predicateList.add(criteriaBuilder.gt(productEntityRoot.get("discountPrice"), 0));
        }
        //sort
        Order sortOrder = null;
        if (sort != null) {
            String[] sortArr = sort.split(",");
            if (sortArr.length == 2) {
                if (sortArr.length == 2) {
                    if (sortArr[1].equalsIgnoreCase("DESC")) {
                        sortOrder = criteriaBuilder.desc(productEntityRoot.get(sortArr[0]));
                    } else {
                        sortOrder = criteriaBuilder.asc(productEntityRoot.get(sortArr[0]));
                    }
                } else {//АВ если не передали тип сортировки
                    sortOrder = criteriaBuilder.asc(productEntityRoot.get(sortArr[0]));
                }
            }
            if (sortOrder == null) {//АВ сортировка по умолчанию
                sortOrder = criteriaBuilder.asc(productEntityRoot.get("name"));
            }
        }
        productEntityCriteriaQuery.select(productEntityRoot)
                .where(criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()])))
                .orderBy(sortOrder);
        return entityManager.createQuery(productEntityCriteriaQuery).getResultList();
        }
    }