package com.ssafy.s103.user;

import com.ssafy.s103.domain.member.application.repository.MemberRepository;
import com.ssafy.s103.domain.member.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    @Commit
    void testRegister() {
        Member member = Member.builder()
            .email("id@test.com")
            .password(bCryptPasswordEncoder.encode("pw"))
            .build();
        Member saved = memberRepository.save(member);
        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved).isEqualTo(member);
    }
}