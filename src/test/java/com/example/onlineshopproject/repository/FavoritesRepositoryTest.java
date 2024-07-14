package com.example.onlineshopproject.repository;

import com.example.onlineshopproject.entity.FavoriteEntity;
import com.example.onlineshopproject.entity.FavoriteEntity;
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
public class FavoritesRepositoryTest {
    private static final long FAVORITE_TEST_ID = 1;
    private static final long USER_TEST_ID  = 1;
    private static FavoriteEntity testNewFavoriteEntity;
    @Autowired
    private FavoriteRepository favoriteRepository;
    @BeforeAll
    static void setUp(){
        testNewFavoriteEntity = new FavoriteEntity();
        testNewFavoriteEntity.setFavoriteId(FAVORITE_TEST_ID);
        testNewFavoriteEntity.setProductEntity(new ProductEntity());
        testNewFavoriteEntity.setUserEntity(new UserEntity());
    }
    @Test
    void getAll(){
        Optional<FavoriteEntity> favorite = favoriteRepository.findById(FAVORITE_TEST_ID);
        Assertions.assertTrue(favorite.isPresent());
        Assertions.assertEquals(FAVORITE_TEST_ID, favorite.get().getFavoriteId());
        Assertions.assertEquals(USER_TEST_ID, favorite.get().getUserEntity().getUserId());
    }
    @Test
    void insert(){
        FavoriteEntity returnFavorite = favoriteRepository.save(testNewFavoriteEntity);
        Assertions.assertNotNull(returnFavorite);
        Assertions.assertTrue(returnFavorite.getFavoriteId() > 0);
        Optional<FavoriteEntity> findCart = favoriteRepository.findById(returnFavorite.getFavoriteId());
        Assertions.assertTrue(findCart.isPresent());
        Assertions.assertEquals(testNewFavoriteEntity.getFavoriteId(),findCart.get().getFavoriteId());
    }
    @Test
    void edit(){
        Optional<FavoriteEntity> FavoriteEntityOptional = favoriteRepository.findById(FAVORITE_TEST_ID);
        Assertions.assertTrue(FavoriteEntityOptional.isPresent());
        FavoriteEntity getCart = FavoriteEntityOptional.get();
        Assertions.assertEquals(FAVORITE_TEST_ID, getCart.getFavoriteId());
        FavoriteEntity returnFavorite = favoriteRepository.save(getCart);
        Assertions.assertNotNull(returnFavorite);
        Assertions.assertEquals(FAVORITE_TEST_ID, returnFavorite.getFavoriteId());
        Optional<FavoriteEntity> findCart = favoriteRepository.findById(FAVORITE_TEST_ID);
        Assertions.assertTrue(findCart.isPresent());
        Assertions.assertEquals("Andrii Kpi", findCart.get().getUserEntity().getName());
    }
    @Test
    void delete(){
        FavoriteEntity returnFavorite = favoriteRepository.save(testNewFavoriteEntity);
        Assertions.assertNotNull(returnFavorite);
        Assertions.assertTrue(returnFavorite.getFavoriteId() > 0);
        Optional<FavoriteEntity> findCart = favoriteRepository.findById(returnFavorite.getFavoriteId());
        Assertions.assertTrue(findCart.isPresent());
        favoriteRepository.delete(findCart.get());
        Optional<FavoriteEntity> findCartAfterDelete = favoriteRepository.findById(returnFavorite.getFavoriteId());
        Assertions.assertFalse(findCartAfterDelete.isPresent());
    }
}
