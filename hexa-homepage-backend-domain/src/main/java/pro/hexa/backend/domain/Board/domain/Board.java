package pro.hexa.backend.domain.Board.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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

    @Comment(value = "게시글")
    @Column(length = 3000)
    private String content;

    @Comment("댓글")
    @OneToMany(mappedBy="board", 
        fetch = FetchType.LAZY, 
        cascade = CascadeType.REMOVE, 
        orphanRemoval = true)
    private List<ParentBoardComment> parentBoardComments = new ArrayList<>();


    @Comment(value = "작성자")
    @Column
    private int writerUser;

    @Comment(value = "추천")
    @Column
    private int recommend;

    @Comment(value = "스크랩")
    @Column
    private int scrap;
}
