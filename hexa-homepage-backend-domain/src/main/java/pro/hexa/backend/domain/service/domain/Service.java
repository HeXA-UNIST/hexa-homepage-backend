package pro.hexa.backend.domain.service.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import pro.hexa.backend.domain.abstract_activity.domain.AbstractActivity;
import pro.hexa.backend.domain.attachment.domain.Attachment;

@Entity(name = "service")
@Getter
public class Service extends AbstractActivity {
    @Comment("썸네일")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Attachment thumbnail;

    @Comment("사이트 주소")
    @Column(length = 127)
    private String siteLink;

    @Comment("깃헙주소")
    @Column(length = 127)
    private String githubLink;
}
