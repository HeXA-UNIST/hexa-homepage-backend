package pro.hexa.backend.domain.model.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
public abstract class AbstractEntity {

    @Comment("생성일자")
    @Column(nullable = false, updatable = false)
    @CreatedDate
    @Getter
    private LocalDateTime createdAt;

    @Comment("변경일자")
    @LastModifiedDate
    @Getter
    @Setter
    private LocalDateTime updatedAt;
}
