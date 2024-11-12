package com.ssafy.s103.domain.member.controller;

import com.ssafy.s103.domain.member.application.service.MemberService;
import com.ssafy.s103.domain.member.dto.request.MemberRegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid MemberRegisterRequest request) {
        memberService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
