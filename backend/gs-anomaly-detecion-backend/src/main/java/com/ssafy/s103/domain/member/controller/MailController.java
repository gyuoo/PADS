package com.ssafy.s103.domain.member.controller;

import static com.ssafy.s103.global.exception.ErrorCode.CODE_MISMATCH;
import static com.ssafy.s103.global.exception.ErrorCode.EMAIL_NOT_FOUND;
import static com.ssafy.s103.global.exception.SuccessCode.SEND_MAIL_SUCCESS;
import static com.ssafy.s103.global.exception.SuccessCode.VERIFICATION_SUCCESS;

import com.ssafy.s103.domain.member.application.service.MailService;
import com.ssafy.s103.domain.member.dto.request.MemberEmailVerifyRequest;
import com.ssafy.s103.domain.member.dto.request.MemberVerificationCodeRequest;
import com.ssafy.s103.global.response.ApiResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
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
    public ApiResponse<HashMap<String, Object>> sendMail(
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

        return new ApiResponse<>(SEND_MAIL_SUCCESS.getMessage(), SEND_MAIL_SUCCESS.getCode(), map,
            null);
    }

    // 인증번호 일치 여부 확인
    @GetMapping("/check")
    public ApiResponse<Boolean> checkMail(
        @RequestBody MemberVerificationCodeRequest memberVerificationCodeRequest) {

        boolean hasEmail = verificationCodes.containsKey(memberVerificationCodeRequest.email());
        if (!hasEmail) {
            return new ApiResponse<>(EMAIL_NOT_FOUND.getMessage(), EMAIL_NOT_FOUND.getCode(), false,
                null);
        }

        boolean isMatch = memberVerificationCodeRequest.code().equals(String.valueOf(
            verificationCode));
        if (!isMatch) {
            return new ApiResponse<>(CODE_MISMATCH.getMessage(), CODE_MISMATCH.getCode(), false,
                null);
        }

        verificationCodes.remove(memberVerificationCodeRequest.email());
        return new ApiResponse<>(VERIFICATION_SUCCESS.getMessage(), VERIFICATION_SUCCESS.getCode(),
            true,
            null);
    }
}
