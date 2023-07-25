package pro.hexa.backend.domain.model.model;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import lombok.Getter;
import pro.hexa.backend.domain.attachment.domain.Attachment;

@MappedSuperclass
@Getter
public abstract class AbstractFile extends AbstractEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attachment_id", nullable = false)
    protected Attachment attachment;
}

