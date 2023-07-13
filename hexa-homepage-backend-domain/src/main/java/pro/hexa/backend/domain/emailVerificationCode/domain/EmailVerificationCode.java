package pro.hexa.backend.domain.emailVerificationCode.domain;

import java.sql.Timestamp;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;

@Entity(name = "아이디찾기 인증코드")
@Getter
public class EmailVerificationCode {

    @Id
    @Column(name = "이메일")
    private String email;

    @Comment("이름")
    @Column(length = 8)
    private String name;

    @Comment("코드")
    @Column(length = 127)
    private String code;

    @CreationTimestamp
    @Comment("토큰이 생성된 날짜")
    private Timestamp availableFrom;

    public static EmailVerificationCode create(String name, String email) {
        EmailVerificationCode emailVerificationCode = new EmailVerificationCode();
        emailVerificationCode.name = name;
        emailVerificationCode.email = email;
        emailVerificationCode.code = UUID.randomUUID().toString();
        return emailVerificationCode;
    }
}
