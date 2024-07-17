//package com.example.onlineshopproject.scheduled;
//
//import com.example.onlineshopproject.enums.Status;
//import com.example.onlineshopproject.repository.OrderRepository;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.TaskScheduler;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.sql.Timestamp;
//import java.util.Arrays;
//import java.util.List;
//import java.util.concurrent.ScheduledFuture;
//
//@Component
//@Slf4j
//@Data
//public class OrderStatusScheduler {
//    private final OrderRepository orderRepository;
//    private final TaskScheduler taskScheduler;
//    private static final Timestamp START_DATE = Timestamp.valueOf("2024-07-15 10:10:10.102321");
//    private volatile boolean active = true;
//    private ScheduledFuture<?> scheduledFuture;
//
//    @Scheduled(initialDelay = 30_000, fixedRate = 30_000)
//    public void updateOrderStatus() {
//        if (!active) return;
//        List<Status> statusToUpdate = Arrays.asList(
//                Status.ORDERED,
//                Status.PAID,
//                Status.CANCELLED,
//                Status.DELIVERED,
//                Status.ON_THE_WAY,
//                Status.PENDING_PAYMENT);
//        Integer updatedOrders = orderRepository.updateOrderStatuses(START_DATE, statusToUpdate);
//        log.info("Number of orders updated:{}", updatedOrders);
//        Integer undeliveredOrders = orderRepository.countUndeliveredOrders(START_DATE);
//        if (undeliveredOrders == 0) {
//            stopScheduler();
//        }
//    }
//
//    public void stopScheduler() {
//        active = false;
//        if (scheduledTask != null) {
//            scheduledTask.cancel(false);
//        }
//        log.info("All orders are delivered. scheduler stopped.");
//    }
//
//    //подготовка к запуску шедулера по триггеру
//    public void startScheduler() {
//        if (scheduledTask == null || scheduledTask.isCancelled) {
//            active = true;
//            scheduledTask = taskScheduler.scheduleAtFixedRate(this::updateOrderStatus);
//            log.info("Scheduler started.");
//        } else {
//            log.info("Scheduler is already running.");
//        }
//    }
//}
