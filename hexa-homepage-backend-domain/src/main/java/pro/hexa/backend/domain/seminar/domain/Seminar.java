package pro.hexa.backend.domain.seminar.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import pro.hexa.backend.domain.abstract_activity.domain.AbstractActivity;
import pro.hexa.backend.domain.attachment.domain.Attachment;
import pro.hexa.backend.domain.user.domain.User;

@Entity(name = "seminar")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Seminar extends AbstractActivity {

    @Comment("날짜")
    @Column
    private LocalDate date;

    @Comment("작성자")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Comment("첨부파일")
    @OneToMany(fetch = FetchType.LAZY)//TODO: handle: , cascade = CascadeType.REMOVE, orphanRemoval = true
    private List<Attachment> attachments = new ArrayList<>();

    public static Seminar create(
        LocalDate date,
        User user,
        String title,
        String content,
        List<Attachment> attachments
    ) {
        Seminar seminar = new Seminar();
        seminar.date = date;
        seminar.user = user;
        seminar.title = title;
        seminar.content = content;
        seminar.attachments = attachments;
        return seminar;
    }

    public void update(
            LocalDate date,
            User user,
            String title,
            String content,
            List<Attachment> attachments
    ) {
        this.date = date;
        this.user = user;
        this.title = title;
        this.content = content;
        this.attachments = attachments;

    }
}
