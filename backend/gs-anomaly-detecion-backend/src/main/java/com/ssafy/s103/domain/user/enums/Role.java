package com.ssafy.s103.domain.user.enums;

public enum Role {
    ROLE_ADMIN("admin");
    private final String description;

    Role(String description) {
        this.description = description;
    }
}
