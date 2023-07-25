package pro.hexa.backend.main.api.domain.login.service;

import lombok.extern.slf4j.Slf4j;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
public class emailService {

    private static final String SMTP_HOST = "smtp_host";
    private static final String SMTP_PORT = "smtp_port";
    private static final String SMTP_USERNAME = "smtp_username";
    private static final String SMTP_PASSWORD = "smtp_password";

    public static void sendVerificationCodeByEmail(String email, String verificationCode) {

        // SMTP 서버 설정
        Properties properties = new Properties();
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        //인증 정보(Authenticator)는 SMTP 서버에 접속할 때 필요한 사용자 이름과 비밀번호를 제공하기 위해 사용됨.
        //이메일을 보내는 경우 일반적으로 SMTP 서버가 사용자 인증을 요구하므로, 사용자 이름과 비밀번호를 인증 정보로 제공해야 함.
        // SMTP 인증 정보
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
            }
        };
        //세션(Session)은 이메일을 전송하기 위해 필요한 환경 설정 정보를 담고 있는 객체.
        //SMTP 서버의 호스트, 포트, 보안 설정 등을 세션 객체에 설정함으로써 JavaMail API가 이메일을 전송할 때 해당 설정을 적용할 수 있음.
        // 세션 생성
        Session session = Session.getDefaultInstance(properties, authenticator);

        try {
            // 이메일 메시지 생성
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTP_USERNAME));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Verification Code");
            message.setText("Your verification code: " + verificationCode);

            // 이메일 전송
            Transport.send(message);

            log.info("Verification code sent to: " + email);

        } catch (MessagingException e) {
            log.error("Failed to send verification code to: " + email);
            e.printStackTrace();
        }
    }
}
