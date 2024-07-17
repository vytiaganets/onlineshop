//package com.example.onlineshopproject.configuration;
//
//import com.example.onlineshopproject.bot.TelegramBot;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.telegram.telegrambots.meta.TelegramBotsApi;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
//@Slf4j
//@Configuration
//public class TelegramConfiguration {
//
//    @Bean
//    public TelegramBot telegramBot(@Value("${bot.name}") String botName,
//                                   @Value("${bot.token}") String botToken) {
//        TelegramBot telegramBot = new TelegramBot(botName, botToken);
//        try {
//            var telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//            telegramBotsApi.registerBot(telegramBot);
//        } catch (TelegramApiException e){
//            log.error("Exception during registration Telegram API: {}", e.getMessage());
//            throw new RuntimeException(e);
//        }
//        return telegramBot;
//    }
//}
