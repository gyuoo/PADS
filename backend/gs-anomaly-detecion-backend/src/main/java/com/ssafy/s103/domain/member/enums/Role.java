package com.ssafy.s103.domain.member.enums;

public enum Role {
    ROLE_ADMIN("admin");
    private final String description;

    Role(String description) {
        this.description = description;
    }
}
