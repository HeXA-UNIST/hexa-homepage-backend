package pro.hexa.backend.domain.news.domain;

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
import pro.hexa.backend.domain.news.model.NEWS_TYPE;

@Entity(name = "news")
@Getter
public class News extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private Long id;

    @Comment(value = "뉴스 종류")
    @Enumerated(value = EnumType.STRING)
    @Column(length = 2)
    private NEWS_TYPE newsType;

    @Comment(value = "제목")
    @Column
    private String title;

    @Comment(value = "세부 내용")
    @Column(length = 3000)
    private String detail;
}
