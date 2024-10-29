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

    // 비밀번호를 암호화된 상태로 설정
    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

}
