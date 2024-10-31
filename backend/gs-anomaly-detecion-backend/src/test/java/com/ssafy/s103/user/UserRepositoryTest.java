package com.ssafy.s103.user;

import com.ssafy.s103.domain.user.application.repository.UserRepository;
import com.ssafy.s103.domain.user.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    @Commit
    void testRegister() {
        User user = User.builder()
            .email("id@test.com")
            .password(bCryptPasswordEncoder.encode("pw"))
            .build();
        User saved = userRepository.save(user);
        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved).isEqualTo(user);
    }
}