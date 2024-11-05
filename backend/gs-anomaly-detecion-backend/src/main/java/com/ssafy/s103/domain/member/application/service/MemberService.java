package com.ssafy.s103.domain.member.application.service;

import com.ssafy.s103.domain.member.application.repository.MemberRepository;
import com.ssafy.s103.domain.member.dto.request.MemberRegisterRequest;
import com.ssafy.s103.domain.member.entity.Member;
import com.ssafy.s103.domain.member.exception.EmailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void save(MemberRegisterRequest memberRegisterRequest) {
        String email = memberRegisterRequest.email();

        if (memberRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        memberRepository.save(Member.builder()
            .email(email)
            .password(bCryptPasswordEncoder.encode(memberRegisterRequest.password()))
            .build());
    }

//    public boolean login(String rawPassword, UserLoginRequestDto userLoginRequestDto) {
//        User user = userRepository.findByEmail(userLoginRequestDto.email())
//            .orElseThrow(() -> new UsernameNotFoundException(userLoginRequestDto.email()));
//        return passwordChecker(rawPassword, user.getPassword());
//    }
//
//    public boolean passwordChecker(String rawPassword, String hashedPassword) {
//        return bCryptPasswordEncoder.matches(rawPassword, hashedPassword);
//    }

}