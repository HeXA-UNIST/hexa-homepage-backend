package pro.hexa.backend.service;

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
    private JavaMailSender mailSender;

    @Value("")
    private String HEXA_EMAIL_ADDRESS;

    public void send(EmailRequestDto mailDto) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(mailDto.getSendTo());
        simpleMailMessage.setFrom(HEXA_EMAIL_ADDRESS);
        simpleMailMessage.setSubject(mailDto.getSubject());
        simpleMailMessage.setText(mailDto.getText());
        mailSender.send(simpleMailMessage);

    }
}
