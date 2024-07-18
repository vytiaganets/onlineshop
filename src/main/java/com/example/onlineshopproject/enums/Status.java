package com.example.onlineshopproject.enums;

public enum Status {
    ORDERED("Создан"),//ожидает оплаты Отменено boolean
    PAID("Оплачен"),//в пути
    ON_THE_WAY("В пути"),//доставлен
    PENDING_PAYMENT("Ожидается оплата"),//оплачен Отменено boolean
    DELIVERED("Доставлено"),//конечный
    CANCELLED("Отменено");//конечный
    private String title;

    Status(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
