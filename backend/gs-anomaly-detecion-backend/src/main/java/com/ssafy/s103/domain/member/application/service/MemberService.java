package com.ssafy.s103.domain.member.application.service;

import com.ssafy.s103.domain.member.application.repository.MemberRepository;
import com.ssafy.s103.domain.member.dto.request.MemberRegisterRequest;
import com.ssafy.s103.domain.member.entity.Member;
import com.ssafy.s103.domain.member.exception.EmailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public void save(MemberRegisterRequest memberRegisterRequest) {
        String email = memberRegisterRequest.email();

        if (memberRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        String encodedPassword = bCryptPasswordEncoder.encode(memberRegisterRequest.password());
        Member member = memberRegisterRequest.toEntity(encodedPassword);
        memberRepository.save(member);
    }
}
