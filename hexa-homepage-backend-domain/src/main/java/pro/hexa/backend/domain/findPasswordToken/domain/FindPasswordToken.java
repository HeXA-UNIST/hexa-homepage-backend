package pro.hexa.backend.domain.findPasswordToken.domain;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;

@Entity(name = "findPasswordToken")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindPasswordToken {

    @Id
    @Column(name = "user_id")
    private String id;

    @Comment("토큰")
    @Column(length = 127)
    private String token;

    @CreationTimestamp
    @Comment("토큰이 생성된 날짜")
    private Timestamp availableFrom;

    public static FindPasswordToken create(String id) {
        FindPasswordToken findPasswordToken=new FindPasswordToken();
        findPasswordToken.id = id;
        findPasswordToken.token = UUID.randomUUID().toString();
        return findPasswordToken;
    }
}
