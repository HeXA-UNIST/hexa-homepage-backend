package pro.hexa.backend.domain.Board.domain;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;

@Entity(name = "parentBoardComment")
@Getter
public class ParentBoardComment extends BoardComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parentBoardComment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="board_id")
    private Board board;

    @OneToMany(mappedBy="parentComment")
    private List<ChildBoardComment> childBoardComments = new ArrayList<ChildBoardComment>();

}
