package com.skala.nav7.global.auth.email.service;

import com.skala.nav7.global.auth.email.error.EmailErrorCode;
import com.skala.nav7.global.auth.email.error.EmailException;
import jakarta.mail.AuthenticationFailedException;
import jakarta.mail.MessagingException;
import jakarta.mail.SendFailedException;
import jakarta.mail.internet.MimeMessage;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class EmailSenderService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final EmailVerificationService emailCodeService;
    private static final String SIGN_UP_TITLE = "[NAV] 이메일 인증번호 안내";

    @Async
    public void sendSignUpEmail(String email) {
        String code = emailCodeService.generateCode(email);
        String html = setVerificationContext(code);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            helper.setTo(email); //메일 받는 사람
            helper.setSubject(SIGN_UP_TITLE);
            helper.setText(html, true);
            javaMailSender.send(mimeMessage);
            log.info("Email sent successfully to {}", email);
        } catch (MessagingException e) {
            Throwable cause = e.getCause();
            handleEmailError(e, cause);
        }
    }

    //Mail template
    public String setVerificationContext(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("email-verification", context);
    }

    private static void handleEmailError(MessagingException e, Throwable cause) {
        if (cause instanceof SendFailedException) {
            log.error("유효하지 않은 이메일 주소: {}", e.getMessage(), e);
            throw new EmailException(EmailErrorCode.INVALID_EMAIL_ADDRESS);
        }

        if (cause instanceof AuthenticationFailedException) {
            log.error("SMTP 인증 실패: {}", e.getMessage(), e);
            throw new EmailException(EmailErrorCode.SMTP_AUTH_FAILED);
        }

        if (cause instanceof SocketTimeoutException || cause instanceof ConnectException) {
            log.error("이메일 서버 연결 실패: {}", e.getMessage(), e);
            throw new EmailException(EmailErrorCode.SMTP_CONNECTION_FAILED);
        }

        log.error("이메일 전송 실패: {}", e.getMessage(), e);
        throw new EmailException(EmailErrorCode.EMAIL_SENDING_FAILED);
    }

}
