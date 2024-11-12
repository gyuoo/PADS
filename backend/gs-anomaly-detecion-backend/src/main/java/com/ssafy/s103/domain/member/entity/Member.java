package com.ssafy.s103.domain.member.entity;

import com.ssafy.s103.domain.member.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L; // 직렬화 ID 추가

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public Member(String email, String password) {
        this.email = email;
        this.username = extractNameFromEmail(email);
        this.password = password;
        this.role = Role.ROLE_MEMBER;
    }

    private String extractNameFromEmail(String email) {
        return email.split("@")[0];
    }

}
