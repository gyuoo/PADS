package com.ssafy.s103.domain.member.controller;

import com.ssafy.s103.domain.member.application.service.MailService;
import com.ssafy.s103.domain.member.dto.request.MemberEmailVerifyRequest;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        String email = memberEmailVerifyRequest.email();
        if (mailService.isPresent(email)) {
            return ResponseEntity.badRequest().build();
        }
        mailService.sendMail(memberEmailVerifyRequest.email());
        return ResponseEntity.ok().build();
    }

    // 인증번호 일치 여부 확인
    @GetMapping("/check")
    public ResponseEntity<Map<String, Boolean>> checkMail(
            @RequestParam("email") String email,
            @RequestParam("code") String code) {
        boolean isVerify = mailService.verifyEmailCode(email, code);
        Map<String, Boolean> response = new HashMap<>();
        response.put("success", isVerify);

        if (!isVerify) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
}
