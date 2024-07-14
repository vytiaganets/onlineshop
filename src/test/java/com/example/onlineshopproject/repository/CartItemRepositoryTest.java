package com.example.onlineshopproject.repository;

import com.example.onlineshopproject.entity.CartEntity;
import com.example.onlineshopproject.entity.CartItemEntity;
import com.example.onlineshopproject.entity.ProductEntity;
import com.example.onlineshopproject.entity.UserEntity;
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
    private static final Long TEST_ID = 1L;
    private static final String TEST_NAME = "test";
    private static CartItemEntity cartItemEntity;
    @Autowired
    private CartItemRepository cartItemRepository;
    @BeforeAll
    static void setUp(){
        cartItemEntity = new CartItemEntity();
        cartItemEntity.setCartItemId(TEST_ID);
        cartItemEntity.setCartEntity(new CartEntity(1L, null, new UserEntity(1L,
                null, null, null, null, null, null, null, null)));
        cartItemEntity.setProductEntity(new ProductEntity(1L, null, null, null, null,null, null,null, null, null,null
                ,null));
        cartItemEntity.setQuantity(5);
    }
    @Test
    void getAll(){
        Optional<CartItemEntity> cartItemEntityOptional = cartItemRepository.findById(TEST_ID);
        Assertions.assertTrue(cartItemEntityOptional.isPresent());
        ///Question: org.opentest4j.AssertionFailedError:
        //Expected :true
        //Actual   :false
        Assertions.assertEquals(TEST_ID, cartItemEntityOptional.get().getCartItemId());
        ///Question
        //org.opentest4j.AssertionFailedError:
        //Expected :true
        //Actual   :false
    }
    @Test
    void insert(){
        CartItemEntity returnCartItem = cartItemRepository.save(cartItemEntity);
        Assertions.assertNotNull(returnCartItem);
        Assertions.assertTrue(returnCartItem.getCartItemId()>0);
        Optional<CartItemEntity> findCartItem = cartItemRepository.findById(returnCartItem.getCartItemId());
        Assertions.assertTrue(findCartItem.isPresent());
        Assertions.assertEquals(cartItemEntity.getCartItemId(), findCartItem.get().getCartItemId());
        ///Question
        //org.opentest4j.AssertionFailedError:
        //Expected :1
        //Actual   :2
        //<Click to see difference>
    }
    @Test
    void edit(){
        Optional<CartItemEntity> cartItemEntityOptional = cartItemRepository.findById(TEST_ID);
        Assertions.assertTrue(cartItemEntityOptional.isPresent());
        ///Question
        //org.opentest4j.AssertionFailedError:
        //Expected :true
        //Actual   :false
        //<Click to see difference>
        CartItemEntity getCartItem = cartItemEntityOptional.get();
        Assertions.assertEquals(TEST_ID, getCartItem.getCartItemId());
        getCartItem.getProductEntity().setName(TEST_NAME);
        CartItemEntity returnCartItem = cartItemRepository.save(getCartItem);
        Assertions.assertNotNull(returnCartItem);
        Assertions.assertEquals(TEST_ID, returnCartItem.getCartItemId());
        Optional<CartItemEntity> findCartItem = cartItemRepository.findById(TEST_ID);
        Assertions.assertTrue(findCartItem.isPresent());
        Assertions.assertEquals(TEST_NAME, findCartItem.get().getProductEntity().getName());
    }
    @Test
    void delete(){
        CartItemEntity returnCartItem = cartItemRepository.save(cartItemEntity);
        Assertions.assertNotNull(returnCartItem);
        Assertions.assertTrue(returnCartItem.getCartItemId() > 0);
        Optional<CartItemEntity> findCartItem = cartItemRepository.findById(returnCartItem.getCartItemId());
        Assertions.assertTrue(findCartItem.isPresent());
        Assertions.assertEquals(cartItemEntity.getCartItemId(), findCartItem.get().getCartItemId());
        cartItemRepository.delete(findCartItem.get());
        Optional<CartItemEntity> findCartItemAfterDelete = cartItemRepository.findById(returnCartItem.getCartItemId());
        Assertions.assertFalse(findCartItemAfterDelete.isPresent());
    }
}