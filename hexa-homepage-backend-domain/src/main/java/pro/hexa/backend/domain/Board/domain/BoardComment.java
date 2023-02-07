package pro.hexa.backend.domain.Board.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Comment;
import pro.hexa.backend.domain.model.model.AbstractEntity;

@MappedSuperclass
public class BoardComment extends AbstractEntity {

    @Comment(value = "내용")
    @Column(length = 300)
    private String content;

    @Comment(value = "작성자")
    private int writerUser;

}
