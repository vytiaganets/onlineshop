package com.example.onlineshopproject.repository;

import com.example.onlineshopproject.entity.CartEntity;
import com.example.onlineshopproject.entity.CartItemEntity;
import com.example.onlineshopproject.entity.ProductEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles(profiles = {"dev"})
public class CartItemRepositoryTest {
    private static final long TEST_ID = 1;
    private static final String TEST_NAME = "test";
    private static CartItemEntity cartItemEntity;
    @Autowired
    private CartItemRepository cartItemRepository;
    @BeforeAll
    static void setUp(){
        cartItemEntity = new CartItemEntity();
        cartItemEntity.setCartItemId(TEST_ID);
        cartItemEntity.setCartEntity(new CartEntity());
        cartItemEntity.setProductEntity(new ProductEntity());
    }
    @Test
    void getAll(){
        Optional<CartItemEntity> cartItemEntityOptional = cartItemRepository.findById(TEST_ID);
        Assertions.assertTrue(cartItemEntityOptional.isPresent());
        Assertions.assertEquals(TEST_ID, cartItemEntityOptional.get().getCartItemId());
    }
    @Test
    void insert(){
        CartItemEntity returnCartItem = cartItemRepository.save(cartItemEntity);
        Assertions.assertNotNull(returnCartItem);
        Assertions.assertTrue(returnCartItem.getCartItemId()>0);
        Optional<CartItemEntity> findCartItem = cartItemRepository.findById(returnCartItem.getCartItemId());
        Assertions.assertTrue(findCartItem.isPresent());
        Assertions.assertEquals(cartItemEntity.getCartItemId(), findCartItem.get().getCartItemId());
    }
}





























