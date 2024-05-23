package pro.hexa.backend.domain.attachment.domain;

import java.nio.file.Path;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import pro.hexa.backend.domain.model.model.AbstractEntity;

@Entity(name = "attachment")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Attachment extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id")
    private Long id;

    @Comment(value = "위치")
    @Column(length = 200)
    private String location;

    @Comment(value = "이름")
    @Column(length = 100)
    private String name;

    @Comment(value = "용량")
    @Column
    private Long size;

    public static Attachment create(String fileUploadLocation, String name, Long size) {
        Attachment attachment = new Attachment();
        attachment.location = Path.of(fileUploadLocation, attachment.getId().toString()).toString();
        attachment.name = name;
        attachment.size = size;
        return attachment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
