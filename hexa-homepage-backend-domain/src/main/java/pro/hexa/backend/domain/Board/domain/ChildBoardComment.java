package pro.hexa.backend.domain.Board.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;

@Entity(name = "ChildBoardComment")
@Getter
public class ChildBoardComment extends BoardComment {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parentBoardComment_id")
    private Board parentComment;
}
