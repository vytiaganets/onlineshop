package com.example.onlineshopproject.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

//@Configuration
//@EnableScheduling//AV Включение планирования
//@EnableAsync
//@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)//AV Включение планирования с помощью свойства
public class ScheduledConfiguration {
}
