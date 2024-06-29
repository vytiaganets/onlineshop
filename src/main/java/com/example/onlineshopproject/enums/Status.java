package com.example.onlineshopproject.enums;

public enum Status {
    ORDERED("Создан"),
    PAID("Оплачен"),
    ON_THE_WAY(""),
    PENDING_PAYMENT("Ожидается оплата"),
    DELIVERED("Доставлено"),
    CANCELLED("Отменено");
    private String title;

    Status(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
