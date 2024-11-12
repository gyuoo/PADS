package com.ssafy.s103.domain.member.controller;

import com.ssafy.s103.domain.member.application.service.MailService;
import com.ssafy.s103.domain.member.dto.request.MemberEmailVerifyRequest;
import com.ssafy.s103.domain.member.dto.request.MemberVerificationCodeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mails")
public class MailController {

    private final MailService mailService;

    // 인증 이메일 전송
    @PostMapping("/send")
    public ResponseEntity<Void> sendMail(
        @RequestBody MemberEmailVerifyRequest memberEmailVerifyRequest) {
        mailService.sendMail(memberEmailVerifyRequest.email());
        return ResponseEntity.ok().build();
    }

    // 인증번호 일치 여부 확인
    @GetMapping("/check")
    public ResponseEntity<Void> checkMail(
        @RequestBody MemberVerificationCodeRequest memberVerificationCodeRequest) {
        String email = memberVerificationCodeRequest.email();
        String code = memberVerificationCodeRequest.code();
        
        boolean isVerify = mailService.verifyEmailCode(email, code);
        if (!isVerify) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
