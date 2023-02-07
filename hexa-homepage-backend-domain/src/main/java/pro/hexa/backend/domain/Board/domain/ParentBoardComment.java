package pro.hexa.backend.domain.Board.domain;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Comment;

import lombok.Getter;

@Entity(name = "parentBoardComment")
@Getter
public class ParentBoardComment extends BoardComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parentBoardComment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    private Board board;

    @Comment("대댓글")
    @OneToMany(mappedBy="parentComment",
        fetch = FetchType.LAZY, 
        cascade = CascadeType.REMOVE, 
        orphanRemoval = true)
    private List<ChildBoardComment> childBoardComments = new ArrayList<>();

}
