package com.example.onlineshopproject.bot;

import org.springframework.data.repository.CrudRepository;

public interface TelegramUserRepository extends CrudRepository<TelegramUser, Long> {
}
