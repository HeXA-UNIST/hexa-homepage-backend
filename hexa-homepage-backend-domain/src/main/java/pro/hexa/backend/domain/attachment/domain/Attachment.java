package pro.hexa.backend.domain.attachment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import lombok.Getter;
import pro.hexa.backend.domain.model.model.AbstractEntity;

@Entity(name = "attachment")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
public class Attachment extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id")
    private Long id;

}
