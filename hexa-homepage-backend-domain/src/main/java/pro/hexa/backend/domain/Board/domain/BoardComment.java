package pro.hexa.backend.domain.Board.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Comment;
import pro.hexa.backend.domain.model.model.AbstractEntity;

@Entity
@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BoardComment extends AbstractEntity {

    @Comment(value = "내용")
    @Column(length = 300)
    private String content;

    @Comment(value = "작성자")
    @Column
    private int writerUser;

}
