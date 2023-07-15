package pro.hexa.backend.domain.AuthenticationNumber.domain;

import lombok.Getter;
import org.hibernate.annotations.Comment;
import pro.hexa.backend.domain.user.domain.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
public class AuthenticationNumber {
    public static final int LifeTimeOfAuthenticationNumber = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "authenticationNumber_id")
    private Long id;

    @Comment("생성시기")
    private LocalDateTime createdAt;

    @Comment("난수 인증 번호")
    private String randomAuthenticationNumbers;

    @Comment("유저")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;



    public AuthenticationNumber(LocalDateTime createdAt, String randomAuthenticationNumbers, User user) {
        this.createdAt = createdAt;
        this.randomAuthenticationNumbers = randomAuthenticationNumbers;
        this.user = user;
    }
}
