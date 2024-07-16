package com.example.onlineshopproject.repository;

import com.example.onlineshopproject.entity.CartEntity;
import com.example.onlineshopproject.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles(profiles = {"dev"})
public class CartRepositoryTest {
    private static final long CART_TEST_ID = 1;
    private static final long USER_TEST_ID = 1;
    private static final UserEntity testUserEntity = new UserEntity();
    private static CartEntity testNewCartEntity;
    @Autowired
    private CartRepository cartRepository;
    @BeforeAll
    static void setUp(){
        testUserEntity.setName("Test");
        testNewCartEntity = new CartEntity();
        testNewCartEntity.setCartItemEntitySet(new HashSet());
        testNewCartEntity.setUserEntity(new UserEntity());
    }
    @Test
    void getAll(){
        Optional<CartEntity> cartEntityOptional = cartRepository.findById(CART_TEST_ID);
        Assertions.assertTrue(cartEntityOptional.isPresent());
        Assertions.assertEquals(CART_TEST_ID, cartEntityOptional.get().getCartId());
        Assertions.assertEquals(USER_TEST_ID, cartEntityOptional.get().getUserEntity().getUserId());
    }
//    @Test
//    void insert(){
//        CartEntity returnCart = cartRepository.save(testNewCartEntity);
//        ///Question
//        //could not execute statement [NULL not allowed for column "USERID"; SQL statement:
//        //insert into Cart (UserId,CartID) values (?,default) [23502-224]] [insert into Cart (UserId,CartID) values (?,default)]; SQL [insert into Cart (UserId,CartID) values (?,default)]; constraint [null]
//        Assertions.assertNotNull(returnCart);
//        Assertions.assertTrue(returnCart.getCartId() > 0);
//        Optional<CartEntity> findCart = cartRepository.findById(returnCart.getCartId());
//        Assertions.assertTrue(findCart.isPresent());
//        Assertions.assertEquals(testNewCartEntity.getCartId(),findCart.get().getCartId());
//    }
    @Test
    void edit(){
        Optional<CartEntity> cartEntityOptional = cartRepository.findById(CART_TEST_ID);
        Assertions.assertTrue(cartEntityOptional.isPresent());
        CartEntity getCart = cartEntityOptional.get();
        Assertions.assertEquals(CART_TEST_ID, getCart.getCartId());
        getCart.setUserEntity((testUserEntity));
        CartEntity returnCart = cartRepository.save(getCart);
        Assertions.assertNotNull(returnCart);
        Assertions.assertEquals(CART_TEST_ID, returnCart.getCartId());
        Optional<CartEntity> findCart = cartRepository.findById(CART_TEST_ID);
        Assertions.assertTrue(findCart.isPresent());
        Assertions.assertEquals("Test", findCart.get().getUserEntity().getName());
    }
//    @Test
//    void delete(){
//        CartEntity returnCart = cartRepository.save(testNewCartEntity);
//        ///Question
//        //could not execute statement [NULL not allowed for column "USERID"; SQL statement:
//        //insert into Cart (UserId,CartID) values (?,default) [23502-224]] [insert into Cart (UserId,CartID) values (?,default)]; SQL [insert into Cart (UserId,CartID) values (?,default)]; constraint [null]
//        Assertions.assertNotNull(returnCart);
//        Assertions.assertTrue(returnCart.getCartId() > 0);
//        Optional<CartEntity> findCart = cartRepository.findById(returnCart.getCartId());
//        Assertions.assertTrue(findCart.isPresent());
//        cartRepository.delete(findCart.get());
//        Optional<CartEntity> findCartAfterDelete = cartRepository.findById(returnCart.getCartId());
//        Assertions.assertFalse(findCartAfterDelete.isPresent());
//    }
}