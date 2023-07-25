package pro.hexa.backend.domain.Board.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Comment;

import lombok.Getter;
import pro.hexa.backend.domain.model.model.AbstractEntity;
import pro.hexa.backend.domain.user.domain.User;

@Entity(name = "board_comment")
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BoardComment extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_comment_id")
    private Long id;

    @Comment(value = "내용")
    @Column(length = 300)
    private String content;

    @Comment(value = "작성자")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User writerUser;

}
