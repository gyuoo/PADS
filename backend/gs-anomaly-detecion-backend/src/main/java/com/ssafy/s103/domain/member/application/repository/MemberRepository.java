package com.ssafy.s103.domain.member.application.repository;

import com.ssafy.s103.domain.member.entity.Member;
import com.ssafy.s103.domain.member.exception.MemberNotFoundException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    default Member findUser(String email) {
        return findByEmail(email).orElseThrow(MemberNotFoundException::new);
    }

    Optional<Member> findByEmail(String email);

}
