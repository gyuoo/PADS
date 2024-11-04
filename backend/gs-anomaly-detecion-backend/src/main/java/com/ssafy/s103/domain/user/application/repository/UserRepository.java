package com.ssafy.s103.domain.user.application.repository;

import com.ssafy.s103.domain.user.entity.User;
import com.ssafy.s103.domain.user.exception.UserNotFoundException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    default User findUser(String email) {
        return findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    Optional<User> findByEmail(String email);

}
