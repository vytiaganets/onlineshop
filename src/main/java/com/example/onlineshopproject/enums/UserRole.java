package com.example.onlineshopproject.enums;

public enum UserRole {
    ADMIN("Роль администратора"),
    USER("Роль пользователя");
    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
