package com.ssafy.s103.domain.user.application.service;

import static com.ssafy.s103.global.exception.ErrorCode.EMAIL_SEND_ERROR;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private int number;

    @Value("${spring.mail.username}")
    private String senderEmail;

    // 랜덤으로 숫자 생성
    public void createNumber() {
        number = (int) (Math.random() * (90000)) + 100000;
    }

    public MimeMessage createMail(String mail) {
        createNumber();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("[Pads] 인증 번호 안내 드립니다.");
            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다. 아래 번호를 입력해 주세요." + "</h3>";
            body += "<h1>" + number + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body, "UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
    }

    public int sendMail(String mail) {
        MimeMessage message = createMail(mail);
        try {
            javaMailSender.send(message); // 메일 발송
        } catch (MailException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(EMAIL_SEND_ERROR.getMessage());
        }

        return number;
    }
}