package com.example.onlineshopproject.repository;

import com.example.onlineshopproject.entity.CategoryEntity;
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
public class CategoryRepositoryTest {
    private static final long CATEGORY_TEST_ID = 1;
    private static final long USER_TEST_ID = 1;
    private static final UserEntity testUserEntity = new UserEntity();
    private static CategoryEntity testNewCategoryEntity;
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeAll
    static void setUp() {
        testUserEntity.setName("Test");
        testNewCategoryEntity = new CategoryEntity();
        testNewCategoryEntity.setCategoryId(CATEGORY_TEST_ID);
        testNewCategoryEntity.setProducts(new HashSet());
        testNewCategoryEntity.setName("Category Test");
    }

    @Test
    void getAll() {
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(CATEGORY_TEST_ID);
        Assertions.assertTrue(categoryEntityOptional.isPresent());
        Assertions.assertEquals(CATEGORY_TEST_ID, categoryEntityOptional.get().getCategoryId());
    }

    @Test
    void insert() {
        CategoryEntity returnCategory = categoryRepository.save(testNewCategoryEntity);
        Assertions.assertNotNull(returnCategory);
        Assertions.assertTrue(returnCategory.getCategoryId() > 0);
        Optional<CategoryEntity> findCategory = categoryRepository.findById(returnCategory.getCategoryId());
        Assertions.assertTrue(findCategory.isPresent());
        Assertions.assertEquals(testNewCategoryEntity.getCategoryId(), findCategory.get().getCategoryId());
    }

    @Test
    void edit() {
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(CATEGORY_TEST_ID);
        Assertions.assertTrue(categoryEntityOptional.isPresent());
        CategoryEntity getCategory = categoryEntityOptional.get();
        Assertions.assertEquals(CATEGORY_TEST_ID, getCategory.getCategoryId());
        CategoryEntity returnCategory = categoryRepository.save(getCategory);
        Assertions.assertNotNull(returnCategory);
        Assertions.assertEquals(CATEGORY_TEST_ID, returnCategory.getCategoryId());
        Optional<CategoryEntity> findCategory = categoryRepository.findById(CATEGORY_TEST_ID);
        Assertions.assertTrue(findCategory.isPresent());
        Assertions.assertEquals("Category Test", findCategory.get().getName());
        ///Question
        //org.opentest4j.AssertionFailedError:
        //Expected :Category Test
        //Actual   :Protective products and septic tanks
        //<Click to see difference>
    }

    @Test
    void delete() {
        CategoryEntity returnCategory = categoryRepository.save(testNewCategoryEntity);
        Assertions.assertNotNull(returnCategory);
        Assertions.assertTrue(returnCategory.getCategoryId() > 0);
        Optional<CategoryEntity> findCategory = categoryRepository.findById(returnCategory.getCategoryId());
        Assertions.assertTrue(findCategory.isPresent());
        categoryRepository.delete(findCategory.get());
        Optional<CategoryEntity> findCategoryAfterDelete = categoryRepository.findById(returnCategory.getCategoryId());
        Assertions.assertFalse(findCategoryAfterDelete.isPresent());
    }
}
