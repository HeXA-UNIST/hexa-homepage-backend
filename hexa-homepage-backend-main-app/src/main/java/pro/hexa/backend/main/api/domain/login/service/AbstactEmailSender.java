package pro.hexa.backend.main.api.domain.login.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.annotation.Transactional;
import pro.hexa.backend.main.api.domain.login.dto.EmailRequestDto;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;


@Getter
public abstract class EmailSender {
    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;
    private String AuthenticationNumbers;
    EmailSender(JavaMailSender javaMailSender, RedisUtil redisUtil){
        this.javaMailSender = javaMailSender;
        this.redisUtil = redisUtil;
    }

    @Transactional
    public void authEmail(EmailRequestDto request){
        // 임의의 authKey 생성
        Random random = new Random();
        String authKey = String.valueOf(random.nextInt(888888) + 111111);
        this.AuthenticationNumbers = authKey;
        // 이메일 발송
        sendAuthEmail(request.getEmail(), authKey);
    }
    public String getAuthenticationNumbers(){
        return this.AuthenticationNumbers;
    }
    private void sendAuthEmail(String email, String authKey){
        String subject = "제목";
        String text = "회원 가입을 위한 인증번호는 " + authKey + "입니다";

        /// javax.mail을 import할 수 없어서 작성이 안되네요.
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(mimeMessage);
        }catch(MessagingException e){
            e.printStackTrace();
        }

        // 유효시간 (5분)간 {email, authKey} 저장
        redisUtil.setDataExpire(authKey, email, 60 * 5L);
    }
}
