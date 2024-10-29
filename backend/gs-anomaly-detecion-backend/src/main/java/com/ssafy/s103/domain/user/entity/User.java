package com.ssafy.s103.domain.user.entity;

import com.ssafy.s103.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String password;

    @Column(length = 250, nullable = false, unique = true)
    private String email;

    @Column(length = 512)
    private String refreshToken;

    @Builder
    public User(String password, String email) {
        this.name = extractNameFromEmail(email);
        this.password = password;
        this.email = email;
    }

    // 이메일에서 '@' 앞부분만 가져옴
    private String extractNameFromEmail(String email) {
        return email.split("@")[0];
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    public void updateRefreshTokenByLogin(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void deleteRefreshTokenByLogout() {
        this.refreshToken = null;
    }
}
