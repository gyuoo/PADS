package com.ssafy.s103.domain.member.controller;

import com.ssafy.s103.domain.member.application.service.MailService;
import com.ssafy.s103.domain.member.dto.request.MemberEmailVerifyRequest;
import com.ssafy.s103.domain.member.dto.request.MemberVerificationCodeRequest;
import java.util.HashMap;
import java.util.Map;
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

    private final Map<String, String> verificationCodes = new HashMap<>();
    private final MailService mailService;
    private int verificationCode; // 이메일 인증 번호

    // 인증 이메일 전송
    @PostMapping("/send")
    public ResponseEntity<Void> sendMail(
        @RequestBody MemberEmailVerifyRequest memberEmailVerifyRequest) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            verificationCode = mailService.sendMail(memberEmailVerifyRequest.email());
            String code = String.valueOf(verificationCode);
            verificationCodes.put(memberEmailVerifyRequest.email(), code);
            map.put("success", Boolean.TRUE);
            map.put("verificationCode", code);
        } catch (Exception e) {
            map.put("success", Boolean.FALSE);
            map.put("error", e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    // 인증번호 일치 여부 확인
    @GetMapping("/check")
    public ResponseEntity<Void> checkMail(
        @RequestBody MemberVerificationCodeRequest memberVerificationCodeRequest) {

        boolean hasEmail = verificationCodes.containsKey(memberVerificationCodeRequest.email());
        if (!hasEmail) {
            return ResponseEntity.notFound().build();
        }

        boolean isMatch = memberVerificationCodeRequest.code().equals(String.valueOf(
            verificationCode));
        if (!isMatch) {
            return ResponseEntity.badRequest().build();
        }

        verificationCodes.remove(memberVerificationCodeRequest.email());
        return ResponseEntity.ok().build();
    }
}
