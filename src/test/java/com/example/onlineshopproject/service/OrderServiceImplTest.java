package com.example.onlineshopproject.service;

import com.example.onlineshopproject.dto.*;
import com.example.onlineshopproject.entity.*;
import com.example.onlineshopproject.enums.DeliveryMethod;
import com.example.onlineshopproject.enums.Status;
import com.example.onlineshopproject.enums.UserRole;
import com.example.onlineshopproject.exceptions.OrderNotFoundException;
import com.example.onlineshopproject.mapper.Mappers;
import com.example.onlineshopproject.repository.OrderItemRepository;
import com.example.onlineshopproject.repository.OrderRepository;
import com.example.onlineshopproject.repository.ProductRepository;
import com.example.onlineshopproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class OrderServiceImplTest {
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private OrderRepository orderRepositoryMock;
    @Mock
    private OrderItemRepository orderItemRepositoryMock;
    @Mock
    private ProductRepository productRepositoryMock;
    @InjectMocks
    private OrderServiceImpl orderServiceMock;
    @Mock
    private Mappers mappersMock;
    OrderNotFoundException orderNotFoundException;
    private UserEntity userEntity;
    private OrderEntity orderEntity;
    private OrderItemEntity orderItemEntity;
    private ProductEntity productEntity;
    private UserResponseDto userResponseDto;
    private OrderResponseDto orderResponseDto;
    private OrderItemResponseDto orderItemResponseDto;
    private ProductResponseDto productResponseDto;
    private OrderRequestDto orderRequestDto, incorrectOrderRequestDto;
    private OrderItemRequestDto orderItemRequestDto, incorrectOrderItemRequestDto;
    Set<OrderItemRequestDto> orderItemRequestDtoSet = new HashSet<>();
    Set<OrderItemRequestDto> incorrectOrderItemRequestDtoSet = new HashSet<>();
    @BeforeEach
    void setUp(){
        userEntity = new UserEntity(1L,
                "Andrii Kpi",
                "andrii@ukr.net",
                "123456789012",
                "1234",
                UserRole.USER,
                null,
                null,
                null);
        orderEntity = new OrderEntity(
                1L,
                "Berlin, Central station",
                "123456789012",
                DeliveryMethod.COURIER_DELIVERY,
                Status.PAID,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                userEntity,
                null,
                null
        );
        productEntity = new ProductEntity(1L,
                "Product name",
                "Product description",
                new BigDecimal("10.00"),
                "http://localhost/product.jpg",
                new BigDecimal("1.00"),
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                new CategoryEntity(1L,
                        "Category name",
                        null),
                null,
                null,
                null);
        orderItemEntity = new OrderItemEntity(
                1L,
                null,
                productEntity,
                3,
                new BigDecimal("3.00"));
    Set<OrderEntity> orderEntitySet = new HashSet<>();
    orderEntitySet.add(orderEntity);
    userEntity.setOrderEntitySet(orderEntitySet);
        userResponseDto = UserResponseDto
                .builder()
                .userId(1L)
                .name("Andrey Kpi")
                .email("andey.kpi@ukr.net")
                .phone("123456789012")
                .password("1234")
                .userRole(UserRole.USER)
                .build();
        orderResponseDto = OrderResponseDto
                .builder()
                .orderId(1L)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .deliveryAddress("Berlin, Central station")
                .contactPhone("123456789012")
                .deliveryMethod(DeliveryMethod.COURIER_DELIVERY)
                .status(Status.PAID)
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .orderItemResponseDtoSet(null)
                .userResponseDto(userResponseDto)
                .build();
        productResponseDto = ProductResponseDto
                .builder()
                .productId(1L)
                .name("Name")
                .description("Description")
                .price(new BigDecimal("10.00"))
                .imageURL("http://localhost/cart.jpg")
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .categoryResponseDto(new CategoryResponseDto(1L, "Category"))
                .build();
        orderItemResponseDto = OrderItemResponseDto
                .builder()
                .orderItemId(1L)
                .orderResponseDto(null)
                .productResponseDto(productResponseDto)
                .quantity(3)
                .build();
        Set<OrderItemResponseDto> orderItemResponseDtoSet = new HashSet<>();
        orderItemResponseDtoSet.add(orderItemResponseDto);
        orderResponseDto.setOrderItemResponseDtoSet(orderItemResponseDtoSet);
        orderItemRequestDto = OrderItemRequestDto
                .builder()
                .productId(1L)
                .quantity(3)
                .build();
        orderItemRequestDtoSet.add(orderItemRequestDto);
        incorrectOrderItemRequestDto = OrderItemRequestDto
                .builder()
                .productId(9L)
                .quantity(2)
                .build();
        incorrectOrderItemRequestDtoSet.add(incorrectOrderItemRequestDto);
        orderRequestDto = OrderRequestDto
                .builder()
                .orderItemSet(orderItemRequestDtoSet)
                .deliveryAddress("Berlin, Central station")
                .deliveryMethod("COURIER_DELIVERY")
                .build();
        incorrectOrderRequestDto = OrderRequestDto
                .builder()
                .orderItemSet(incorrectOrderItemRequestDtoSet)
                .deliveryAddress("Berlin, Central station")
                .deliveryMethod("COURIER_DELIVERY")
                .build();
    }
//    @Test
//    void getById(){
//        Long orderId = 1L;
//        Long incorrectOrderId = 9L;
//        when(orderRepositoryMock.findById(orderId)).thenReturn(Optional.of(orderEntity));
//        when(mappersMock.convertToOrderResponseDto(any(OrderEntity.class))).thenReturn(orderResponseDto);
//        when(mappersMock.convertToOrderItemResponseDto(any(OrderItemEntity.class))).thenReturn(orderItemResponseDto);
//        OrderResponseDto actualOrderResponseDto = orderServiceMock.getById(orderId);
//        verify(orderRepositoryMock, times(1)).findById(orderId);
//        verify(mappersMock, times(1)).convertToOrderResponseDto(any(OrderEntity.class));
//        verify(mappersMock, times(1)).convertToOrderItemResponseDto(any(OrderItemEntity.class));
//        assertNotNull(actualOrderResponseDto);
//        assertEquals(orderResponseDto.getOrderId(), actualOrderResponseDto.getOrderId());
//        assertFalse(actualOrderResponseDto.getOrderItemResponseDtoSet().isEmpty());
//        assertEquals(orderResponseDto.getOrderItemResponseDtoSet().size(),
//                actualOrderResponseDto.getOrderItemResponseDtoSet().size());
//        assertEquals(orderResponseDto.getOrderItemResponseDtoSet().hashCode(),
//                actualOrderResponseDto.getOrderItemResponseDtoSet().hashCode());
//        when(orderRepositoryMock.findById(incorrectOrderId)).thenReturn(Optional.empty());
//        orderNotFoundException = assertThrows(OrderNotFoundException.class,
//                () -> orderServiceMock.getById(incorrectOrderId));
//        assertEquals("Data not found in database.", orderNotFoundException.getMessage());
//        ///Question
//        //java.lang.NullPointerException: Cannot invoke "java.util.Set.stream()" because "set" is null
//    }
//    @Test
//    void getHistoryByUserId(){
//        Long userId = 1L;
//        Long incorrectUserId = 9L;
//        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(userEntity));
//        when(mappersMock.convertToOrderResponseDto(any(OrderEntity.class))).thenReturn(orderResponseDto);
//        when(mappersMock.convertToOrderItemResponseDto(any(OrderItemEntity.class))).thenReturn(orderItemResponseDto);
//        Set<OrderResponseDto> orderResponseDtoSet = new HashSet<>();
//        orderResponseDtoSet.add(orderResponseDto);
//        Set<OrderResponseDto> actualOderResponseDtoSet = orderServiceMock.getHistoryByUserId(userId);
//        verify(userRepositoryMock, times(1)).findById(userId);
//        verify(mappersMock,times(1)).convertToOrderResponseDto(any(OrderEntity.class));
//        verify(mappersMock, times(1)).convertToOrderItemResponseDto(any(OrderItemEntity.class));
//        assertFalse(actualOderResponseDtoSet.isEmpty());
//        assertEquals(orderResponseDtoSet.size(), actualOderResponseDtoSet.size());
//        assertEquals(orderResponseDtoSet.hashCode(), actualOderResponseDtoSet.hashCode());
//        when(userRepositoryMock.findById(incorrectUserId)).thenReturn(Optional.empty());
//        orderNotFoundException = assertThrows(OrderNotFoundException.class,
//                () -> orderServiceMock.getHistoryByUserId(incorrectUserId));
//        assertEquals("Data not found in database.", orderNotFoundException.getMessage());
//        when(userEntity.getOrderEntitySet() == null).thenThrow(orderNotFoundException);
//        orderNotFoundException = assertThrows(OrderNotFoundException.class,
//                () -> orderServiceMock.getHistoryByUserId(incorrectUserId));
//        assertEquals("Data not found in database.", orderNotFoundException.getMessage());
//        //        ///Question
////        //java.lang.NullPointerException: Cannot invoke "java.util.Set.stream()" because "set" is null
//    }
    @Test
    void insert(){
        Long userId = 1L;
        Long incorrectUserId = 9L;
        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(userEntity));
        OrderEntity orderToInsert = new OrderEntity();
        //orderToInsert.setUserEntity(userEntity);
        orderToInsert.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        orderToInsert.setContactPhone(userEntity.getPhoneNumber());
        orderToInsert.setDeliveryAddress(orderRequestDto.getDeliveryAddress());
        orderToInsert.setDeliveryMethod(DeliveryMethod.valueOf(orderRequestDto.getDeliveryMethod()));
        orderToInsert.setStatus(Status.PAID);
        for(OrderItemRequestDto orderItem : orderItemRequestDtoSet){
            when(productRepositoryMock.findById(orderItem.getProductId())).thenReturn(Optional.of(productEntity));
        }
        Set<OrderItemEntity> orderItemEntitySet = new HashSet<>();
        OrderItemEntity orderItemEntityToInsert = new OrderItemEntity();
        orderItemEntityToInsert.setProductEntity(productEntity);
        if(productEntity.getDiscountPrice() == null){
            orderItemEntityToInsert.setPriceAtPurchase(productEntity.getPrice());
        } else {
            orderItemEntityToInsert.setPriceAtPurchase(productEntity.getDiscountPrice());
        }
        orderItemEntityToInsert.setQuantity(orderItemEntity.getQuantity());
        //orderItemEntityToInsert.setOrderEntity(orderToInsert);
        when(orderItemRepositoryMock.save(any(OrderItemEntity.class))).thenReturn(orderItemEntityToInsert);
        orderItemEntitySet.add(orderItemEntityToInsert);
        orderToInsert.setOrderItemEntityHashSet(orderItemEntitySet);
        when(orderRepositoryMock.save(any(OrderEntity.class))).thenReturn(orderToInsert);
        orderServiceMock.insert(orderRequestDto, userId);
        verify(orderRepositoryMock, times(2)).save(any(OrderEntity.class));
        verify(orderItemRepositoryMock,times(1)).save(any(OrderItemEntity.class));
        when(userRepositoryMock.findById(incorrectUserId)).thenReturn(Optional.empty());
        orderNotFoundException = assertThrows(OrderNotFoundException.class,
                () -> orderServiceMock.insert(orderRequestDto, incorrectUserId));
        assertEquals("Data not found in database.", orderNotFoundException.getMessage());
        for (OrderItemRequestDto incorrectOrderItemRequestDto : incorrectOrderItemRequestDtoSet){
            when(productRepositoryMock.findById(incorrectOrderItemRequestDto.getProductId())).thenReturn(Optional.empty());
            orderNotFoundException = assertThrows(OrderNotFoundException.class,
                    () -> orderServiceMock.insert(incorrectOrderRequestDto, userId));
            assertEquals("Data not found in database.", orderNotFoundException.getMessage());
        }
    }
}