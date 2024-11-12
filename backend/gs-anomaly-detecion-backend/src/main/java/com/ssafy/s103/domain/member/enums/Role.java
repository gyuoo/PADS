package com.ssafy.s103.domain.member.enums;

public enum Role {
    ROLE_ADMIN("admin"),
    ROLE_MEMBER("member");
    
    private final String description;

    Role(String description) {
        this.description = description;
    }
}
