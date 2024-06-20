package com.sparta.deventer.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("User"),
    ADMIN("Admin");

    private final String authority;

    UserRole(String authority) {
        this.authority = authority;
    }

}