package pro.hexa.backend.domain.Board.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import pro.hexa.backend.domain.model.model.AbstractEntity;
import pro.hexa.backend.domain.Board.model.BOARD_TYPE;

@Entity(name = "board")
@Getter
public class Board extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Comment(value = "게시글 종류")
    @Enumerated(value = EnumType.STRING)
    @Column(length = 2)
    private BOARD_TYPE boardType;

    @Comment(value = "제목")
    @Column(length = 300)
    private String title;
    
    @Comment(value = "날짜")
    private int date;

    @Comment(value = "게시글")
    @Column(length = 3000)
    private String content;

    @Comment(value = "댓글")
    @Column(length = 300)
    private String comments;//??? 어케 구현하지

    @Comment(value = "작성자")
    private int writerUser;

    @Comment(value = "추천")
    private int recommend;

    @Comment(value = "스크랩")
    private int scrap;
}
