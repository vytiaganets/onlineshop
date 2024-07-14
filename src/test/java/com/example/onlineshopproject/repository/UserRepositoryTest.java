package com.example.onlineshopproject.repository;

import com.example.onlineshopproject.entity.UserEntity;
import com.example.onlineshopproject.enums.UserRole;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles(profiles = {"dev"})
class UserRepositoryTest {
    public static final long TEST_ID = 1L;
    public static final String TEST_NEW_NAME = "NewTestName";
    private static UserEntity testNewUser;
    @Autowired
    private UserRepository userRepositoryTest;

    @BeforeAll
    static void setUp() {
        testNewUser = new UserEntity();
        testNewUser.setName("TestName");
        testNewUser.setEmail("Test E-Mail");
        testNewUser.setRole(UserRole.ADMIN);
        testNewUser.setPasswordHash("Test Password Hash");
        testNewUser.setPhoneNumber("Test Phone Number");
    }

    @Test
    void getAll() {
        Optional<UserEntity> userEntityOptional = userRepositoryTest.findById(TEST_ID);
        assertTrue(userEntityOptional.isPresent());
        assertEquals(TEST_ID, userEntityOptional.get().getUserId());
    }

    @Test
    void insert() {
        UserEntity returnUser = userRepositoryTest.save(testNewUser);
        assertNotNull(returnUser);
        assertTrue(returnUser.getUserId() > 0);
        Optional<UserEntity> findUser = userRepositoryTest.findById(returnUser.getUserId());
        assertTrue(findUser.isPresent());
        assertEquals(testNewUser.getName(), findUser.get().getName());
    }

    @Test
    void edit() {
        Optional<UserEntity> userEntityOptional = userRepositoryTest.findById(TEST_ID);
        assertTrue(userEntityOptional.isPresent());

        UserEntity getUser = userEntityOptional.get();
        assertEquals(TEST_ID, getUser.getUserId());
        getUser.setName(TEST_NEW_NAME);
        UserEntity returnUser = userRepositoryTest.save(getUser);
        assertNotNull(returnUser);
        assertEquals(TEST_ID, returnUser.getUserId());
        Optional<UserEntity> findUser = userRepositoryTest.findById(TEST_ID);
        assertTrue(findUser.isPresent());
        assertEquals(TEST_NEW_NAME, findUser.get().getName());
    }

    @Test
    void delete() {
        UserEntity returnUser = userRepositoryTest.save(testNewUser);
        assertNotNull(returnUser);
        assertTrue(returnUser.getUserId() > 0);
        Optional<UserEntity> findUser = userRepositoryTest.findById(returnUser.getUserId());
        assertTrue(findUser.isPresent());
        assertEquals(testNewUser.getName(), findUser.get().getName());
        userRepositoryTest.delete(findUser.get());
        Optional<UserEntity> findUserAfterDelete = userRepositoryTest.findById((returnUser.getUserId()));
        assertFalse(findUserAfterDelete.isPresent());
    }
}
