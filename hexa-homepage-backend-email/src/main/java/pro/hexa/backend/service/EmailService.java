package main.java.pro.hexa.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pro.hexa.backend.dto.EmailRequestDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String HEXA_EMAIL;

    public void send(EmailRequestDto requestDto) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(requestDto.getSendTo());
        simpleMailMessage.setFrom(HEXA_EMAIL);
        simpleMailMessage.setSubject(requestDto.getName());
        simpleMailMessage.setText(requestDto.getContent());
        mailSender.send(simpleMailMessage);

    }
}
