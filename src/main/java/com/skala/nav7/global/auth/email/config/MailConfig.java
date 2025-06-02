package com.skala.nav7.global.auth.email.config;

import java.util.Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@RequiredArgsConstructor
public class MailConfig {

    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_DEBUG = "mail.smtp.debug";
    private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    private static final String MAIL_SMTP_CONNECTION_TIMEOUT = "mail.smtp.connectiontimeout";
    private static final String MAIL_SMTP_TIMEOUT = "mail.smtp.timeout";
    private static final String MAIL_SMTP_WRITETIMEOUT = "mail.smtp.writetimeout";

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean auth;

    @Value("${spring.mail.properties.mail.smtp.debug}")
    private boolean debug;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean startTlsEnable;

    @Value("${spring.mail.properties.mail.smtp.connectiontimeout}")
    private int connectionTimeout;

    @Value("${spring.mail.properties.mail.smtp.timeout}")
    private int timeout;

    @Value("${spring.mail.properties.mail.smtp.writetimeout}")
    private int writeTimeout;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setDefaultEncoding("UTF-8");

        Properties props = mailSender.getJavaMailProperties();
        props.put(MAIL_SMTP_AUTH, auth);
        props.put(MAIL_SMTP_DEBUG, debug);
        props.put(MAIL_SMTP_STARTTLS_ENABLE, startTlsEnable);
        props.put(MAIL_SMTP_CONNECTION_TIMEOUT, connectionTimeout);
        props.put(MAIL_SMTP_TIMEOUT, timeout);
        props.put(MAIL_SMTP_WRITETIMEOUT, writeTimeout);

        return mailSender;
    }
}