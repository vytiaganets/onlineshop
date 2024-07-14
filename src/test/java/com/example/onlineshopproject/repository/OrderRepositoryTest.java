package com.example.onlineshopproject.repository;

import com.example.onlineshopproject.entity.OrderEntity;
import com.example.onlineshopproject.enums.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.util.Optional;

import static com.example.onlineshopproject.enums.DeliveryMethod.COURIER_DELIVERY;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles(profiles = {"dev"})
public class OrderRepositoryTest {
    private static final long TEST_ID = 1;
    public static final String TEST_NEW_DELIVERY_ADDRESS = "New Delivery Address";
    private static OrderEntity testOrder;
    @Autowired
    private OrderRepository orderRepositoryTest;
    static void setUp(){
        testOrder = new OrderEntity();
        testOrder.setCreatedAt(Timestamp.valueOf("2024-07-02 02:34:21.123456789"));
        testOrder.setDeliveryAddress("TestAddress");
        testOrder.setContactPhone("Test ContactPhone");
        testOrder.setDeliveryMethod(COURIER_DELIVERY);
        testOrder.setStatus(Status.PAID);
        testOrder.setUpdatedAt(Timestamp.valueOf("2024-07-03 02:04:31.123456789"));
    }
    @Test
    void getAll(){
        Optional<OrderEntity> orderEntityOption = orderRepositoryTest.findById(TEST_ID);
        assertTrue(orderEntityOption.isPresent());
        assertEquals(TEST_ID, orderEntityOption.get().getOrderId());
    }
    @Test
    void insert(){
        OrderEntity returnOrder = orderRepositoryTest.save(testOrder);
        ///Question
        //org.springframework.dao.InvalidDataAccessApiUsageException: Entity must not be null
        assertNotNull(returnOrder);
        assertTrue(returnOrder.getOrderId() > 0);
        Optional<OrderEntity> findOrder = orderRepositoryTest.findById(returnOrder.getOrderId());
        assertTrue(findOrder.isPresent());
        assertEquals(testOrder.getDeliveryAddress(), findOrder.get().getDeliveryAddress());
    }
    @Test
    void edit(){
        Optional<OrderEntity> order = orderRepositoryTest.findById(TEST_ID);
        assertTrue(order.isPresent());
        OrderEntity getOrder = order.get();
        assertEquals(TEST_ID, getOrder.getOrderId());
        getOrder.setDeliveryAddress(TEST_NEW_DELIVERY_ADDRESS);
        OrderEntity returnOrder = orderRepositoryTest.save(getOrder);
        assertNotNull(returnOrder);
        assertEquals(TEST_ID, returnOrder.getOrderId());
        Optional<OrderEntity> findOrder = orderRepositoryTest.findById(TEST_ID);
        assertTrue(findOrder.isPresent());
        assertEquals(TEST_NEW_DELIVERY_ADDRESS, findOrder.get().getDeliveryAddress());
    }
    @Test
    void delete(){
        OrderEntity returnOrder = orderRepositoryTest.save(testOrder);
        ///Question org.springframework.dao.InvalidDataAccessApiUsageException: Entity must not be null
        assertNotNull(returnOrder);
        assertTrue(returnOrder.getOrderId() > 0);
        Optional<OrderEntity> findOrder = orderRepositoryTest.findById(returnOrder.getOrderId());
        assertTrue(findOrder.isPresent());
        assertEquals(testOrder.getDeliveryAddress(), findOrder.get().getDeliveryAddress());
        orderRepositoryTest.delete(findOrder.get());
        Optional<OrderEntity> findAfterDelete = orderRepositoryTest.findById(returnOrder.getOrderId());
        assertFalse(findAfterDelete.isPresent());
        ///Question
        //org.springframework.dao.InvalidDataAccessApiUsageException: Entity must not be null
    }
}
