package pro.hexa.backend.domain.sns.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import pro.hexa.backend.domain.model.model.AbstractEntity;
import pro.hexa.backend.domain.sns.model.SNS_TYPE;
import pro.hexa.backend.domain.user.domain.User;

@Entity(name = "sns")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SNS extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sns_id")
    private Long id;

    @Comment("sns 종류")
    @Column(length = 15)
    private SNS_TYPE type;

    @Comment("sns 주소")
    @Column(length = 127)
    private String link;

    @Comment("sns 사용자")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
