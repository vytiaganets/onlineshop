package com.example.onlineshopproject.scheduled;

import com.example.onlineshopproject.entity.OrderEntity;
import com.example.onlineshopproject.enums.Status;
import com.example.onlineshopproject.repository.OrderRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduledService {

    private final OrderRepository orderRepository;
    //AV Executing a task with a delay of 30 seconds
    private Queue<OrderEntity> orderEntityQueue;
    private Random random = new Random();
    private static final Map<Status, Status[]> STATUS_TRANSITIONS = new EnumMap<>(Status.class);

    static {
        STATUS_TRANSITIONS.put(Status.ORDERED,new Status[]{Status.PENDING_PAYMENT, Status.CANCELLED});
        STATUS_TRANSITIONS.put(Status.PENDING_PAYMENT, new Status[]{Status.PAID, Status.CANCELLED});
        STATUS_TRANSITIONS.put(Status.PAID, new Status[]{Status.ON_THE_WAY});
        STATUS_TRANSITIONS.put(Status.ON_THE_WAY, new Status[]{Status.DELIVERED});
        STATUS_TRANSITIONS.put(Status.DELIVERED, null);
        STATUS_TRANSITIONS.put(Status.CANCELLED, null);
    }

    @PostConstruct
    public void initializeQueue() {
        orderEntityQueue = orderRepository
                .findAll()
                .stream()
                .collect(Collectors
                        .toCollection(() ->
        new ConcurrentLinkedQueue<>()));
    }

    @Scheduled(fixedRateString = "${fixedDelay.in.milliseconds}")
    @Async
    @Transactional
    public void scheduleFixedRateTask() {
        while (!orderEntityQueue.isEmpty()) {
            OrderEntity orderEntity = orderEntityQueue.poll();
            Status status = orderEntity.getStatus();
            Status[] possibleStatuses = STATUS_TRANSITIONS.get(status);
            if (possibleStatuses != null) {
                if (possibleStatuses.length == 2) {
                    orderEntity.setStatus(possibleStatuses[random.nextInt(2)]);
                } else {
                    orderEntity.setStatus(possibleStatuses[0]);
                }
                orderRepository.save(orderEntity);
            }
        }
        initializeQueue();
    }
}