package com.example.onlineshopproject.service;

import com.example.onlineshopproject.dto.OrderItemRequestDto;
import com.example.onlineshopproject.configuration.MapperConfiguration;
import com.example.onlineshopproject.dto.OrderItemResponseDto;
import com.example.onlineshopproject.dto.OrderRequestDto;
import com.example.onlineshopproject.dto.OrderResponseDto;
import com.example.onlineshopproject.entity.OrderEntity;
import com.example.onlineshopproject.entity.OrderItemEntity;
import com.example.onlineshopproject.entity.ProductEntity;
import com.example.onlineshopproject.entity.UserEntity;
import com.example.onlineshopproject.enums.DeliveryMethod;
import com.example.onlineshopproject.enums.Status;
import com.example.onlineshopproject.exceptions.NotFoundInDbException;
import com.example.onlineshopproject.mapper.Mappers;
import com.example.onlineshopproject.repository.OrderItemRepository;
import com.example.onlineshopproject.repository.OrderRepository;
import com.example.onlineshopproject.repository.ProductRepository;
import com.example.onlineshopproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final Mappers mappers;

    @Transactional
    public void insert(OrderRequestDto orderRequestDto, Long userId) {
        log.debug("Attempting insert order by userId: {}", userId);
        OrderEntity orderEntity = new OrderEntity();
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        if (userEntity != null) {
            orderEntity.setUserEntity(userEntity);
            orderEntity.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            orderEntity.setContactPhone(userEntity.getPhoneNumber());
            orderEntity.setDeliveryAddress(orderRequestDto.getDeliveryAddress());
            orderEntity.setDeliveryMethod(DeliveryMethod.valueOf(orderRequestDto.getDeliveryMethod()));
            orderEntity.setStatus(Status.ORDERED);
            orderEntity = orderRepository.save(orderEntity);
        } else {
            log.error("Order not found: {}", userId);
            throw new NotFoundInDbException("Data not found in database.");
        }
        Set<OrderItemRequestDto> orderItemRequestDtoSet = orderRequestDto.getOrderItemSet();
        Set<OrderItemEntity> orderItemEntitySet = new HashSet<>();
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        for (OrderItemRequestDto orderItemRequestDto : orderItemRequestDtoSet) {
            ProductEntity productEntity = productRepository.findById(orderItemRequestDto.getProductId()).orElse(null);
            if (productEntity != null) {
                if (productEntity.getDiscountPrice() == null) {
                    orderItemEntity.setPriceAtPurchase(productEntity.getPrice());
                } else {
                    orderItemEntity.setPriceAtPurchase(productEntity.getDiscountPrice());
                }
                orderItemEntity.setQuantity(orderItemEntity.getQuantity());
                orderItemEntity.setQuantity(orderItemRequestDto.getQuantity());
                orderItemEntity.setOrderEntity(orderEntity);
                orderItemRepository.save(orderItemEntity);
                orderItemEntitySet.add(orderItemEntity);
            } else {
                log.error("Order not found: {}", userId);
                throw new NotFoundInDbException("Data not found in database.");
            }
        }
        orderEntity.setOrderItemEntityHashSet(orderItemEntitySet);
        orderRepository.save(orderEntity);
    }


    @Transactional
    public OrderResponseDto getById(Long orderId) {
        log.debug("Attempting receive product by id: {}", orderId);
        OrderEntity orderEntity = orderRepository.findById(orderId).orElse(null);
        if (orderEntity != null) {
            OrderResponseDto orderResponseDto = mappers.convertToOrderResponseDto(orderEntity);
            Set<OrderItemResponseDto> orderItemResponseDtoSet =
                    MapperConfiguration.convertSet(orderEntity.getOrderItemEntityHashSet(),
                            mappers::convertToOrderItemResponseDto);
            orderResponseDto.setOrderItemResponseDtoSet(orderItemResponseDtoSet);
            return orderResponseDto;
        } else {
            log.error("Order wit id {} not found.", orderId);
            throw new NotFoundInDbException("Data not found in database.");
        }
    }

    @Transactional
    public Set<OrderResponseDto> getHistoryByUserId(Long userId) {
        log.debug("Attempting history by user id", userId);
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        if (userEntity != null) {
            Set<OrderEntity> orderEntitySet = userEntity.getOrderEntitySet();
            Set<OrderResponseDto> orderResponseDtoSet = new HashSet<>();
            if (orderEntitySet != null) {
                for (OrderEntity orderEntity : orderEntitySet) {
                    Set<OrderItemResponseDto> orderItemResponseDtoSet =
                            MapperConfiguration.convertSet(orderEntity.getOrderItemEntityHashSet(),
                                    mappers::convertToOrderItemResponseDto);
                    OrderResponseDto orderResponseDto = mappers.convertToOrderResponseDto(orderEntity);
                    orderResponseDto.setOrderItemResponseDtoSet(orderItemResponseDtoSet);
                    orderResponseDtoSet.add(orderResponseDto);
                }
                return orderResponseDtoSet;
            }
            log.error("History by user id not found: {}", userId);
            throw new NotFoundInDbException("Data not found in database.");
        } else {
            log.error("User not found: {}", userId);
            throw new NotFoundInDbException("Data not found in database.");
        }
    }
}
