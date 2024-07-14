package com.example.onlineshopproject.repository;

import com.example.onlineshopproject.entity.ProductEntity;
import com.example.onlineshopproject.entity.UserEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles(profiles = {"dev"})
public class ProductRepositoryTest {
    private static final long TEST_ID = 1L;
    private static final String TEST_NAME = "Test name";
    private static UserEntity userEntity;
    private static ProductEntity productEntity;
    @Autowired
    private ProductRepository productRepository;
    @BeforeAll
    static void setUp(){
        productEntity = new ProductEntity();
        productEntity.setProductId(TEST_ID);
        productEntity.setName("TestName");
        productEntity.setDescription("TestDescription");
        productEntity.setPrice(BigDecimal.valueOf(10.00));
        productEntity.setImageUrl("url");
        productEntity.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        productEntity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
    }
    @Test
    void getAll(){
        Optional<ProductEntity> productEntityOptional = productRepository.findById(TEST_ID);
        assertTrue(productEntityOptional.isPresent());
        assertEquals(TEST_ID, productEntityOptional.get().getProductId());
    }
    @Test
    void insert(){
        ProductEntity returnProduct = productRepository.save(productEntity);
        ///Question
        //java.lang.StackOverflowError
        //
        //	at org.springframework.data.repository.core.support.RepositoryMethodInvoker$RepositoryFragmentMethodInvoker.lambda$new$0(RepositoryMethodInvoker.java:281)
        //	at org.springframework.data.repository.core.support.RepositoryMethodInvoker.doInvoke(RepositoryMethodInvoker.java:170)
        //	at org.springframework.data.repository.core.support.RepositoryMethodInvoker.invoke(RepositoryMethodInvoker.java:158)
        assertNotNull(returnProduct);
        assertTrue(returnProduct.getProductId() > 0);
        Optional<ProductEntity> findProduct = productRepository.findById(returnProduct.getProductId());
        assertTrue(findProduct.isPresent());
        assertEquals(productEntity.getName(), findProduct.get().getName());
    }
    @Test
    void edit(){
        Optional<ProductEntity> productEntityOptional = productRepository.findById(TEST_ID);
        assertTrue(productEntityOptional.isPresent());
        ProductEntity getProduct = productEntityOptional.get();
        assertEquals(TEST_ID, getProduct.getProductId());
        getProduct.setName(TEST_NAME);
        ProductEntity returnProduct = productRepository.save(getProduct);
        assertNotNull(returnProduct);
        assertEquals(TEST_ID, returnProduct.getProductId());
        Optional<ProductEntity> findProduct = productRepository.findById(TEST_ID);
        assertTrue(findProduct.isPresent());
        assertEquals(TEST_NAME, findProduct.get().getName());
    }
    @Test
    void delete(){
        ProductEntity returnProduct = productRepository.save(productEntity);
        ///Question
        //java.lang.StackOverflowError
        //
        //	at org.springframework.data.repository.core.support.RepositoryMethodInvoker$RepositoryFragmentMethodInvoker.lambda$new$0(RepositoryMethodInvoker.java:281)
        //	at org.springframework.data.repository.core.support.RepositoryMethodInvoker.doInvoke(RepositoryMethodInvoker.java:170)
        assertNotNull(returnProduct);
        assertTrue(returnProduct.getProductId() > 0);
        Optional<ProductEntity> findProduct = productRepository.findById(returnProduct.getProductId());
        assertTrue(findProduct.isPresent());
        assertEquals(productEntity.getName(), findProduct.get().getName());
        productRepository.delete(findProduct.get());
        Optional<ProductEntity> findProductAfterDelete = productRepository.findById(returnProduct.getProductId());
        assertFalse(findProductAfterDelete.isPresent());
    }
}
