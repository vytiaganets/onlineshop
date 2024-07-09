package com.example.onlineshopproject.scheduled;

import com.example.onlineshopproject.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
@Service
public class ScheduledService {
    @Autowired
    private OrderRepository orderRepository;
//AV Выполнение задания с задержкой 30сек
    @Scheduled(fixedRateString = "${fixedDelay.in.milliseconds}")

    @Async
    public void scheduleFixedRateTask() {
        System.out.println(
                "Fixed rate task - " + System.currentTimeMillis() / 30000
        );
    }
}
