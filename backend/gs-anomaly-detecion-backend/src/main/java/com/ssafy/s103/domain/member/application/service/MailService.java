package com.ssafy.s103.domain.member.application.service;

import static com.ssafy.s103.global.exception.ErrorCode.EMAIL_SEND_ERROR;

import com.ssafy.s103.domain.member.application.repository.MemberRepository;
import com.ssafy.s103.domain.member.entity.Member;
import com.ssafy.s103.global.util.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MemberRepository memberRepository;
    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public boolean isPresent(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        return member.isPresent();
    }

    public void sendMail(String email) {
        if (redisUtil.existData(email)) {
            redisUtil.deleteData(email);
        }

        MimeMessage message = createMail(email);
        try {
            javaMailSender.send(message); // 메일 발송
        } catch (MailException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(EMAIL_SEND_ERROR.getMessage());
        }
    }

    public Boolean verifyEmailCode(String email, String code) {
        String codeFoundByEmail = redisUtil.getData(email);
        if (codeFoundByEmail == null) {
            return false;
        }
        return codeFoundByEmail.equals(code);
    }

    private MimeMessage createMail(String email) {
        String code = String.valueOf(createNumber());
        MimeMessage message = javaMailSender.createMimeMessage();
        String body = loadHtmlTemplate(code);

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject("[Pads] 인증 번호 안내 드립니다.");
            message.setText(body, "UTF-8", "html");

            // Redis 에 해당 인증코드 인증 시간 설정
            redisUtil.setDataExpire(email, code, 60 * 10L);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
    }

    private int createNumber() {
        return (int) (Math.random() * 900000) + 100000;
    }

    private String loadHtmlTemplate(String code) {
        try {
            ClassPathResource resource = new ClassPathResource("emailBody.html");
            String content = Files.readString(resource.getFile().toPath());
            return content.replace("{{ code }}", code);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load HTML template", e);
        }
    }
}