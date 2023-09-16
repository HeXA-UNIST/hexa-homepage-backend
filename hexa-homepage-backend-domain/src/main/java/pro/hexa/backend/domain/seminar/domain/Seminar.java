package pro.hexa.backend.domain.seminar.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import pro.hexa.backend.domain.abstract_activity.domain.AbstractActivity;
import pro.hexa.backend.domain.attachment.domain.Attachment;
import pro.hexa.backend.domain.user.domain.User;

@Entity(name = "seminar")
@Getter
@Setter
public class Seminar extends AbstractActivity {

    @Comment("날짜")
    @Column
    private LocalDateTime date;

    @Comment("작성자")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Comment("첨부파일")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Attachment> attachments = new ArrayList<>();

    public static Seminar create(
        LocalDateTime date,
        User user,
        List<Attachment> attachments
    ) {
        Seminar seminar = new Seminar();
        seminar.date = date;
        seminar.user = user;
        seminar.attachments = attachments;
        return seminar;
    }
}
