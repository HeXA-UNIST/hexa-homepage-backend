package pro.hexa.backend.domain.Board.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Comment;

import lombok.Getter;
import pro.hexa.backend.domain.model.model.AbstractEntity;
import pro.hexa.backend.domain.user.domain.User;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BoardComment extends AbstractEntity {

    @Comment(value = "내용")
    @Column(length = 300)
    private String content;

    @Comment(value = "작성자")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id")
    private User writerUser;

}
